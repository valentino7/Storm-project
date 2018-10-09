package sdcc2018.storm.control;

import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionControl;
import sdcc2018.storm.entity.Phase;

import java.util.ArrayList;
import java.util.List;


public class CustomMongoUpdateMapperControl implements MongoMapper {

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        IntersectionControl intersectionControl = (IntersectionControl) tuple.getValueByField(Costant.PHASE);
       // int j= 0;
        System.err.println(intersectionControl.getId());
        List<Document> documentList = new ArrayList<>();
        for ( Phase i : intersectionControl.getPhases()){
            Document phase = new Document();
            phase.append( "id", i.getId());
            phase.append( "greenTime",i.getGreen());
            phase.append( "redTime", i.getRed());
            documentList.add(phase);
            //j++;
        }
        document.append("listPhase",documentList);
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperControl withFields(String... fields) {
        return this;
    }
}
