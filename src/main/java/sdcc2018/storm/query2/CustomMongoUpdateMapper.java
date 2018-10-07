package sdcc2018.storm.query2;

import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery2;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;

import java.util.List;


public class CustomMongoUpdateMapper implements MongoMapper {

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        String id = (String) tuple.getValueByField(Costant.ID);
        Double globalMedian = (Double) tuple.getValueByField(Costant.MEDIAN);
        document.append( "id" , id  );
        document.append( "globalMedian" , globalMedian  );
        List<IntersectionQuery2> listToSave= (List<IntersectionQuery2>) tuple.getValueByField(Costant.LIST_INTERSECTION);
        int i = 0;
        for ( IntersectionQuery2 intersection : listToSave){
            Document documentToAnnidate = new Document();
            documentToAnnidate.append( "id", intersection.getId());
            documentToAnnidate.append( "median", intersection.getMedianaVeicoli() );
            document.append( ""+i , documentToAnnidate);
            i++;
        }
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapper withFields(String... fields) {
        return this;
    }
}
