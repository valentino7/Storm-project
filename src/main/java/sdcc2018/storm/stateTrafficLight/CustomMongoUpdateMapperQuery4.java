package sdcc2018.storm.stateTrafficLight;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;

import java.util.ArrayList;
import java.util.List;


public class CustomMongoUpdateMapperQuery4 implements MongoMapper {


    @Override
    public Document toDocument(ITuple tuple) {

        Document document = new Document();
        Sensor s = (Sensor) tuple.getValueByField(Costant.SENSOR);
        List<String> list = new ArrayList<>();
        Document documentToAnnidate = new Document();
        for ( int i = 0 ; i < s.getStateTrafficLight().size() ; i++){
            list.add(s.getStateTrafficLight().get(i));
        }
        document.append("stateTrafficLight",list);
        return new Document("$set", document);



        /*Document document = new Document();
        Sensor s = (Sensor) tuple.getValueByField(Costant.SENSOR);
        List<Document> documentList=new ArrayList<Document>();

        Document trafficLight = new Document();
        trafficLight.append("0",s.getStateTrafficLight()[0]);
        documentList.add(trafficLight);
        trafficLight=null;

        trafficLight=new Document();
        trafficLight.append("1",s.getStateTrafficLight()[1]);
        documentList.add(trafficLight);
        trafficLight=null;

        trafficLight=new Document();
        trafficLight.append("2",s.getStateTrafficLight()[0]);
        documentList.add(trafficLight);

        document.append("stateTrafficLight", documentList);
        return new Document("$set", document);*/
    }

    public CustomMongoUpdateMapperQuery4 withFields(String... fields) {
        return this;
    }
}
