package sdcc2018.storm.stateTrafficLight;

import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;


public class CustomMongoUpdateMapperQuery4 implements MongoMapper {


    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        Sensor s = (Sensor) tuple.getValueByField(Costant.SENSOR);

        Document documentToAnnidate = new Document();
        for ( int i = 0 ; i < s.getStateTrafficLight().length ; i++){
            documentToAnnidate.append(""+i, s.getStateTrafficLight()[i]);
        }
        document.append("stateTrafficLight",documentToAnnidate);
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperQuery4 withFields(String... fields) {
        return this;
    }
}
