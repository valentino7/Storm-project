package sdcc2018.storm.stateTrafficLight;

import com.mongodb.client.model.Filters;
import org.apache.storm.mongodb.common.QueryFilterCreator;

import org.apache.storm.tuple.ITuple;
import org.bson.conversions.Bson;
import sdcc2018.storm.entity.Costant;

public class SimpleQueryFilterCreator implements QueryFilterCreator {


    @Override
    public Bson createFilter(ITuple tuple) {
        return Filters.and(Filters.eq(Costant.ID_INTERSECTION, tuple.getValueByField(Costant.ID_INTERSECTION)), Filters.eq(Costant.ID_SENSOR,tuple.getValueByField(Costant.ID_SENSOR) ) );

    }

    public SimpleQueryFilterCreator withField(String field) {
        return this;
    }

}

