package sdcc2018.storm.query1.bolt;

import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery1;
import sdcc2018.storm.entity.Sensor;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterBolt extends BaseBasicBolt {
//il filter bolt riceve semafori e invia incroci quando essi sono completi
    private HashMap<Integer, IntersectionQuery1> mappa = new HashMap<>();
    private IntersectionQuery1 inc;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.ID,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_15M,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_1H,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_24H,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
    }


    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        List<Sensor> list;
        Sensor s=(Sensor) input.getValueByField(Costant.F_RECORD);
        if ( mappa.containsKey(s.getIntersection()) ){//incrocio esiste in hasmap
            IntersectionQuery1 c;
            c = mappa.get(s.getIntersection());//prendi incrocio dall'hashmap
            c.getL().add(s);//aggiungi il semaforo all'incrocio
            if ( c.getL().size() == Costant.SEM_INTERSEC ){//se l'incrocio è completo
                mappa.remove(s.getIntersection());//rimuovi l'incrocio dall'hashmap
                media( (IntersectionQuery1) c);//calcola la media
                inc=c;
                collector.emit(Costant.STREAM_15M,new Values( s.getIntersection(), inc) );//emetti l'incrocio
                collector.emit(Costant.STREAM_1H,new Values( s.getIntersection(), inc) );//emetti l'incrocio
                collector.emit(Costant.STREAM_24H,new Values( s.getIntersection(), inc) );//emetti l'incrocio

            }
            else{//?
                mappa.put(s.getIntersection(), inc);
            }
        }
        else {//l'incrocio non esiste in hashmap
            list = new ArrayList<>();
            list.add(s);
            inc = new IntersectionQuery1(list, s.getIntersection());//crea incrocio con il semaforo ricevuto
            mappa.put(s.getIntersection(), inc );//metti in hashmap l'incrocio
        }

    }

    private void media(IntersectionQuery1 c) {
        double somma = 0;
        int numeroTotaleVeicoli = 0;
        for ( int i = 0 ; i<Costant.SEM_INTERSEC ; i++){
            somma += c.getL().get(i).getSpeed()*c.getL().get(i).getNumVehicles();
            numeroTotaleVeicoli += c.getL().get(i).getNumVehicles();
        }
        c.setNumeroVeicoli(numeroTotaleVeicoli);
        c.setVelocitaMedia(somma/numeroTotaleVeicoli);
    }
}