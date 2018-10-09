package sdcc2018.storm.control.bolt;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionControl;
import sdcc2018.storm.entity.Sensor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumBolt extends BaseWindowedBolt{
    private OutputCollector collector;
    private HashMap<Integer, IntersectionControl> map;


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.MAP_INTERSECTION));
    }


    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        map = new HashMap<>();
    }

    @Override
    public void execute(TupleWindow inputWindow) {
        List<Tuple> tupleList = inputWindow.get();
        for ( Tuple t : tupleList){
            IntersectionControl l = (IntersectionControl) t.getValueByField(Costant.INTERSECTION);
            if(map.containsKey(l.getId())){
                map.replace(l.getId(), sumVehicles(map.get(l.getId()),l));
            }
            else{
                map.put(l.getId(),l);
            }
        }
        collector.emit(new Values(createList(map)));
    }


    private IntersectionControl sumVehicles(IntersectionControl oldi, IntersectionControl newi){
        List<Sensor> sOld = oldi.getL();
        List<Sensor> sNew = newi.getL();

        for (int i = 0; i < Costant.SEM_INTERSEC; i++) {
            sOld.get(i).setNumVehicles(sNew.get(i).getNumVehicles() + sOld.get(i).getNumVehicles());
        }
        oldi.setL(sOld);
        return oldi;
    }

    private List<IntersectionControl> createList(HashMap<Integer,IntersectionControl> mappa){
        //verifica che da mappa trasforma in lista
        //System.err.println(mappa);
        List<IntersectionControl> list = new ArrayList<>();
        for (IntersectionControl i : mappa.values()) {
            list.add(i);
            mappa.remove(i);
        }
        //System.err.println(list);
        return list;
    }

}
