package sdcc2018.storm.entity.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sdcc2018.storm.entity.Intersection;

import java.io.InputStream;
import java.util.Properties;

public class CreatorIntersection {
    private Properties properties;
    public CreatorIntersection()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }
    public static void main(String args[])throws Exception{
        CreatorIntersection creator=new CreatorIntersection();
        MongoClientURI connectionString = new MongoClientURI(creator.properties.getProperty("urlMongoDB"));
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase(creator.properties.getProperty("mongoDBName"));
        MongoCollection<Document> bookmarksCollection = database.getCollection("sdccIntersection");
        Document document = new Document();

        IntersectionGUI intersection=new IntersectionGUI();

        String title ="miao";
        document.append("name", title);
        String tags="l";
        document.append("tags", tags);
        bookmarksCollection.insertOne(document);
        return;
    }
}
