package sdcc2018.storm.query1;

import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery1;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.tuple.ITuple;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class CustomMongoUpdateMapperQuery1 implements MongoMapper {

    @Override
    public Document toDocument(ITuple tuple) {
        Document document = new Document();
        String id = (String) tuple.getValueByField(Costant.ID_WINDOW);
        document.append( Costant.ID_WINDOW, id  );
        List<IntersectionQuery1> listToSave= (List<IntersectionQuery1>) tuple.getValueByField(Costant.RANK_TOPK);
        List<Document> list = new ArrayList<>();
        for ( int j = 0 ; j < listToSave.size() ; j++){
            IntersectionQuery1 intersection = listToSave.get(j);
            Document documentToAnnidate = new Document();
            documentToAnnidate.append( "rank", j+1 );
            documentToAnnidate.append( Costant.ID_INTERSECTION, intersection.getId());
            documentToAnnidate.append( "meanSpeed", intersection.getVelocitaMedia() );
            list.add(documentToAnnidate);
        }
        document.append( "rankElement", list);
        return new Document("$set", document);
    }

    public CustomMongoUpdateMapperQuery1 withFields(String... fields) {
        return this;
    }
}
