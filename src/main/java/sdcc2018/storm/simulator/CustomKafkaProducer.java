package sdcc2018.storm.simulator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.metrics.Stat;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.mongodb.*;
import sdcc2018.storm.entity.mongodb.CustomSensor;
import sdcc2018.storm.entity.mongodb.IntersectionGUI;
import sdcc2018.storm.entity.mongodb.StateSensor;

public class CustomKafkaProducer {
    private Properties properties;
    public CustomKafkaProducer()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }

    public static void main(String args[]) throws IOException,Exception {
        CustomKafkaProducer customKafkaProducer = new CustomKafkaProducer();
        String kafka_brokers=customKafkaProducer.properties.getProperty("kafka.brokerurl");
        String kafka_topic=customKafkaProducer.properties.getProperty("kafka.topic");
        customKafkaProducer.properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_brokers);
        customKafkaProducer.properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        customKafkaProducer.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        customKafkaProducer.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.connect.json.JsonSerializer");
        KafkaProducer kafkaProducer = new KafkaProducer<>(customKafkaProducer.properties);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MongoClientURI connectionString = new MongoClientURI(customKafkaProducer.properties.getProperty("urlMongoDB"));
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase(customKafkaProducer.properties.getProperty("mongoDBName"));

        while(true) {
            MongoCollection<Document> coll = database.getCollection("sdccIntersection");
            ArrayList<IntersectionGUI> list = new ArrayList<IntersectionGUI>();
            ArrayList<StateSensor> listSensor = new ArrayList<StateSensor>();
            MongoCursor<Document> cursor = coll.find().iterator();
            try {
                while (cursor.hasNext()) {
                    JsonNode rootNode = objectMapper.readTree(cursor.next().toJson());
                    list.add(objectMapper.treeToValue(rootNode, IntersectionGUI.class));
                }
            } finally {
                cursor.close();
            }
            coll = database.getCollection("sdccStateTrafficLight");
            listSensor = new ArrayList<StateSensor>();

            cursor = coll.find().iterator();
            try {
                while (cursor.hasNext()) {
                    JsonNode rootNode = objectMapper.readTree(cursor.next().toJson());
                    listSensor.add(objectMapper.treeToValue(rootNode,StateSensor.class));
                }
            } finally {
                cursor.close();
            }

            Random rand = new Random();
            double max = 80;
            double min = 0;
            Sensor s;
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < Costant.SEM_INTERSEC; j++) {
                    CustomSensor customSensor=list.get(i).getSensorList()[j];
                    StateSensor stateSensor = listSensor.get(4*i+j);
                    double randomNumber=rand.nextDouble();
                    if(randomNumber<Costant.PROB_TO_BREAK){
                        stateSensor.setLightToBroken();
                    }
                    s = new Sensor(i, j, min + rand.nextDouble() * (max - min), ThreadLocalRandom.current().nextInt(0, 100 + 1),customSensor.getSaturation(),customSensor.getLatitude(),customSensor.getLongitude(),stateSensor.getStateTrafficLight());
                    JsonNode jsonNode = objectMapper.valueToTree(s);
                    ProducerRecord<String, JsonNode> recordToSend = new ProducerRecord<>(kafka_topic, jsonNode);
                    System.err.println(recordToSend);
                    kafkaProducer.send(recordToSend);
                }
            }
            list=null;
            listSensor=null;
            System.out.println("end generation");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
