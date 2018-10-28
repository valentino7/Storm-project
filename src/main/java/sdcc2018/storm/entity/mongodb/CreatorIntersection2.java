package sdcc2018.storm.entity.mongodb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;

import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class CreatorIntersection2 {
    private Properties properties;
    public CreatorIntersection2()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }
    public static CustomSensor[] getSensorsRandomPosition(int id,double intersectionLatitude,double intersectionLongitude,double maxDistIntersection,double possibleSaturations[]){
        CustomSensor customSensor[] =new CustomSensor[4];
        Random rand=new Random();
        double maxLen=maxDistIntersection/2;
        double saturation;
        //sensore in alto a sinistra
        int trafficLight=0;
        int randomIndex=rand.nextInt(possibleSaturations.length);
        saturation=possibleSaturations[randomIndex];
        double sensorLatitude=intersectionLatitude+rand.nextDouble()*maxLen;
        double sensorLongitude=intersectionLongitude-rand.nextDouble()*maxLen;
        customSensor[trafficLight]=new CustomSensor(id,trafficLight,saturation,sensorLatitude,sensorLongitude);
        trafficLight++;
        //sensore in alto a destra
        randomIndex=rand.nextInt(possibleSaturations.length);
        saturation=possibleSaturations[randomIndex];
        sensorLatitude=intersectionLatitude+rand.nextDouble()*maxLen;
        sensorLongitude=intersectionLongitude+rand.nextDouble()*maxLen;
        customSensor[trafficLight]=new CustomSensor(id,trafficLight,saturation,sensorLatitude,sensorLongitude);
        trafficLight++;
        //sensore in basso a destra
        randomIndex=rand.nextInt(possibleSaturations.length);
        saturation=possibleSaturations[randomIndex];
        sensorLatitude=intersectionLatitude-rand.nextDouble()*maxLen;
        sensorLongitude=intersectionLongitude+rand.nextDouble()*maxLen;
        customSensor[trafficLight]=new CustomSensor(id,trafficLight,saturation,sensorLatitude,sensorLongitude);
        trafficLight++;
        //sensore in basso a sinistra
        randomIndex=rand.nextInt(possibleSaturations.length);
        saturation=possibleSaturations[randomIndex];
        sensorLatitude=intersectionLatitude-rand.nextDouble()*maxLen;
        sensorLongitude=intersectionLongitude-rand.nextDouble()*maxLen;
        customSensor[trafficLight]=new CustomSensor(id,trafficLight,saturation,sensorLatitude,sensorLongitude);
        trafficLight++;
        return customSensor;
    }

    public void removeCollections(MongoDatabase database){
        MongoCollection<Document> coll = database.getCollection(this.properties.getProperty("collectionNameIntersection"));
        coll.drop();
        coll=database.getCollection(this.properties.getProperty("collectionNameStateTrafficLight"));
        coll.drop();
        System.err.println("collections removed");
    }
    public static void main(String args[])throws Exception{
        //longitudine asse x
        //latitudine asse y
        CreatorIntersection2 creator=new CreatorIntersection2();
        MongoClientURI connectionString = new MongoClientURI(creator.properties.getProperty("urlMongoDB"));
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase(creator.properties.getProperty("mongoDBName"));
        creator.removeCollections(database);
        MongoCollection<Document> bookmarksCollection = database.getCollection(creator.properties.getProperty("collectionNameIntersection"));
        Document document;
        IntersectionGUI intersectionGUI=null;
        ObjectMapper objectMapper=new ObjectMapper();
        double []possibleSaturations={6500,7000,7500,8000,8500};
        double maxDistIntersection=1;
        int numIntersectionLongitude=10;
        int numIntersectionLatitude=5;
        Random rand = new Random();
        double startLongitudeFix=100;
        double startLatitudeFix=80;
        double startLongitude=rand.nextDouble()+startLongitudeFix;
        double startLatitude=rand.nextDouble()+startLatitudeFix;
        double randomLongitude=0;
        double randomLatitude=0;
        double intersectionLatitude;
        double intersectionLongitude;
        int id=0;
        for(int i=0;i<numIntersectionLatitude;i++){
            for(int l=0;l<numIntersectionLongitude;l++){
                if(l==0){
                    randomLongitude=startLongitude;
                    randomLatitude=startLatitude;
                    intersectionLatitude=randomLatitude;
                    intersectionLongitude=randomLongitude;
                }
                else {
                    randomLongitude += rand.nextDouble()*maxDistIntersection;
                    intersectionLatitude = randomLatitude;
                    intersectionLongitude = randomLongitude;
                }
                CustomSensor customSensors[];
                customSensors=getSensorsRandomPosition(id,intersectionLatitude,intersectionLongitude,maxDistIntersection,possibleSaturations);
                System.out.println(customSensors[0]);
                System.out.println(customSensors[1]);
                System.out.println(customSensors[2]);
                System.out.println(customSensors[3]);
                intersectionGUI=new IntersectionGUI(id,customSensors,CustomPhase.randomPhase());
                System.out.println(intersectionGUI);
                JsonNode jsonNode=objectMapper.valueToTree(intersectionGUI);
                System.out.println(jsonNode);
                document = Document.parse(jsonNode.toString() );
                bookmarksCollection.insertOne(document);
                id++;
            }
            startLatitude+=rand.nextDouble()*maxDistIntersection;
        }
        bookmarksCollection = database.getCollection(creator.properties.getProperty("collectionNameStateTrafficLight"));

        for(int id2=0;id2< Costant.N_INTERSECTIONS;id2++){
            StateSensor stateSensor[]=new StateSensor[4];
            for(int j2=0;j2<4;j2++) {
                stateSensor[j2] = new StateSensor(id2, j2);
                JsonNode jsonNode = objectMapper.valueToTree(stateSensor[j2]);
                System.out.println(jsonNode);
                document = Document.parse(jsonNode.toString());
                bookmarksCollection.insertOne(document);
            }
        }
        return;
    }
}
