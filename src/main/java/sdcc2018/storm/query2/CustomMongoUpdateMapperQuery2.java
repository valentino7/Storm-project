package sdcc2018.storm.query2;

import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery2;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class CustomMongoUpdateMapperQuery2 implements MongoMapper {

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        String id = (String) tuple.getValueByField(Costant.ID_WINDOW);
        Double globalMedian = (Double) tuple.getValueByField(Costant.MEDIAN);
        document.append( Costant.ID_WINDOW , id  );
        document.append( "globalMedian" , globalMedian  );
        List<IntersectionQuery2> listToSave= (List<IntersectionQuery2>) tuple.getValueByField(Costant.LIST_INTERSECTION);
        List<Document> list = new ArrayList<>();
        for ( IntersectionQuery2 intersection : listToSave){
            Document documentToAnnidate = new Document();
            documentToAnnidate.append( Costant.ID_INTERSECTION, intersection.getId());
            documentToAnnidate.append( "median", intersection.getMedianaVeicoli() );
            list.add(documentToAnnidate);
        }
        document.append( "medianList" , list);
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperQuery2 withFields(String... fields) {
        return this;
    }
}
