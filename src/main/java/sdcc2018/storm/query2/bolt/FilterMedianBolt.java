package sdcc2018.storm.query2.bolt;

import sdcc2018.digest.AVLTreeDigest;
import sdcc2018.digest.MergingDigest;
import sdcc2018.digest.TDigest;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery2;
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

public class FilterMedianBolt extends BaseBasicBolt {
    //bolt filtro riceve tupla di un sensore e invia una tupla incrocio quando ha ricevuto i 4 semafori
    //dello stesso incrocio
    private HashMap<Integer, IntersectionQuery2> mappa=new HashMap<>();;
    private IntersectionQuery2 inc;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.ID,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_15M,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_1H,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
        declarer.declareStream(Costant.STREAM_24H,new Fields(Costant.ID_WINDOW,Costant.INTERSECTION));
    }
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        List<Sensor> lista = null;
        Sensor s=(Sensor) input.getValueByField(Costant.F_RECORD);

        if ( mappa.containsKey(s.getIntersection()) ){
            IntersectionQuery2 c;
            c = mappa.get(s.getIntersection());
            c.getL().add(s);
            if ( c.getL().size() == Costant.SEM_INTERSEC ){
                mappa.remove(s.getIntersection());
                mediana(c);
                //collector.emit(new Values(c.getId(),c ) );
                collector.emit(Costant.STREAM_15M,new Values(c.getId(),c )  );//emetti l'incrocio
                collector.emit(Costant.STREAM_1H,new Values( c.getId(),c ) );//emetti l'incrocio
                collector.emit(Costant.STREAM_24H,new Values(c.getId(),c )  );//emetti l'incrocio
            }
            else{
                mappa.remove(c.getId());
                mappa.put(s.getIntersection(),c);
            }
        }
        else {
            lista = new ArrayList<>();
            lista.add(s);
            inc = new IntersectionQuery2(lista, s.getIntersection());
            mappa.put(s.getIntersection(), inc );
        }
    }

    private void mediana(IntersectionQuery2 c){
        //calcola mediana dell'incrocio
        //TDigest td1 = new AVLTreeDigest(Costant.COMPRESSION);
        TDigest td1 = new MergingDigest(Costant.COMPRESSION);
        for(int i=0;i!=Costant.SEM_INTERSEC;i++){
            td1.add(c.getL().get(i).getNumVehicles());
        }
        //c.setMedianaVeicoli(td1.quantile(Costant.QUANTIL));
        c.setTd1(td1);
    }

}