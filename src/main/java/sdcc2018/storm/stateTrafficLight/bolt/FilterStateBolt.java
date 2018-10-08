package sdcc2018.storm.stateTrafficLight.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;

public class FilterStateBolt extends BaseBasicBolt {
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.SENSOR));
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        System.err.println(input.getValueByField(Costant.F_RECORD));
        Sensor s=(Sensor) input.getValueByField(Costant.F_RECORD);
        if(s.Broken()) {
            collector.emit(new Values(s));
        }
        return;
    }
}