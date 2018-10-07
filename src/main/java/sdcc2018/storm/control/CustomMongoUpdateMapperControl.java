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
        String id = (String) tuple.getValueByField(Costant.ID);
        document.append( "id" , id  );
        List<IntersectionControl> listToSave= (List<IntersectionControl>) tuple.getValueByField(Costant.RANK_TOPK);
        for ( int j = 0 ; j < listToSave.size() ; j++){
            IntersectionControl intersection = listToSave.get(j);
            Document documentToAnnidate = new Document();
            documentToAnnidate.append( "classifica", j+1 );
            documentToAnnidate.append( "id", intersection.getId());

            // DA FINIRE
            document.append( "valore "+ (j+1) , documentToAnnidate);
        }
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperControl withFields(String... fields) {
        return this;
    }
}
