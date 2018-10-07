package sdcc2018.storm.stateTrafficLight;

import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;


public class CustomMongoUpdateMapperQuery4 implements MongoMapper {
    private String[] fields;

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        Sensor s = (Sensor) tuple.getValueByField(Costant.SENSOR);
        System.out.println(s);
        document.append( "id" ,s.getIntersection());
        Document documentToAnnidate = new Document();
        documentToAnnidate.append("semaforo:", s.getTrafficLight());
        document.append("test",documentToAnnidate);
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperQuery4 withFields(String... fields) {
        this.fields = fields;
        return this;
    }
}
