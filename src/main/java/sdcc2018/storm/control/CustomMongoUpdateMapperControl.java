package sdcc2018.storm.control;

import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionControl;

import java.util.List;


public class CustomMongoUpdateMapperControl implements MongoMapper {

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        List<IntersectionControl> listToSave= (List<IntersectionControl>) tuple.getValueByField(Costant.RANK_TOPK);
        int j= 0;
        document.append(Costant.PHASE,"test");
        for ( IntersectionControl i : listToSave){
            Document documentToAnnidate = new Document();
            documentToAnnidate.append( "fase1-verde", i.getPhases().get(0).getGreen());
            documentToAnnidate.append( "fase2-verde", i.getPhases().get(1).getGreen());
            document.append( ""+j , documentToAnnidate);
            j++;
        }
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperControl withFields(String... fields) {
        return this;
    }
}
