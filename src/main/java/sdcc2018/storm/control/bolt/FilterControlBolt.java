package sdcc2018.storm.control.bolt;


import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionControl;
import sdcc2018.storm.entity.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterControlBolt extends BaseBasicBolt {
    private HashMap<Integer, IntersectionControl> mappa = new HashMap<>();
    private IntersectionControl inc;


    public FilterControlBolt() {
    }
    @Override
    public void execute(Tuple input,BasicOutputCollector collector) {
        List<Sensor> list;
        //controllare integrità tupla e/o semaforo rotto
        Sensor s=(Sensor) input.getValueByField(Costant.F_RECORD);
        if ( mappa.containsKey(s.getIntersection()) ){//incrocio esiste in hasmap
            IntersectionControl c;
            c = mappa.get(s.getIntersection());//prendi incrocio dall'hashmap
            c.getL().add(s);//aggiungi il semaforo all'incrocio
            if ( c.getL().size() == Costant.SEM_INTERSEC ){//se l'incrocio è completo
                mappa.remove(s.getIntersection());//rimuovi l'incrocio dall'hashmap
                inc=c;
                collector.emit(new Values( s.getIntersection(), inc ) );//emetti l'incrocio
            }
            else{
                mappa.put(s.getIntersection(), inc);
            }
        }
        else {//l'incrocio non esiste in hashmap
            list = new ArrayList<>();
            list.add(s);
            inc = new IntersectionControl(list, s.getIntersection());//crea incrocio con il semaforo ricevuto
            mappa.put(s.getIntersection(), inc );//metti in hashmap l'incrocio
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.ID,Costant.INTERSECTION));
    }
}
