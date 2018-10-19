package sdcc2018.storm.query2.bolt;

import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.MergingDigest;
import com.tdunning.math.stats.TDigest;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery2;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalMedianBolt extends BaseBasicBolt {
    private int PreviousReplication;
    private String MedianType ;
    private int countMedian = 0;
    private List<IntersectionQuery2> globalList=new ArrayList<>();
    //private TDigest globalTDIgest = new AVLTreeDigest(Costant.COMPRESSION);
    private TDigest globalTDIgest = new MergingDigest(Costant.COMPRESSION);

    public GlobalMedianBolt(String s, int rep) {
        this.MedianType = s;
        this.PreviousReplication = rep;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.ID_WINDOW, Costant.LIST_INTERSECTION,Costant.MEDIAN));
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        List<IntersectionQuery2> intersections;
        intersections= (List<IntersectionQuery2>) tuple.getValueByField(Costant.LIST_INTERSECTION);
        globalList.addAll(intersections);
        countMedian++;
        for(int i=0;i!= intersections.size() ;i++){
            IntersectionQuery2 inter=intersections.get(i);
            TDigest t=inter.getTd1();
            //globalTDIgest.add(intersections.get(i).getTd1());
           // System.err.println("min : "+ t.getMin() + " max: "+ t.getMax());
            globalTDIgest.add(t);

        }
        if(countMedian >= PreviousReplication) {
            ArrayList<IntersectionQuery2> listMax = new ArrayList<>();
            double quantil = globalTDIgest.quantile(Costant.QUANTIL);
            for ( IntersectionQuery2 i : globalList) {
                if( i.getMedianaVeicoli() >= quantil ){
                    listMax.add(i);
                }
            }
            Collections.sort(listMax,new IntersectionQuery2());
            collector.emit(new Values(this.MedianType,listMax, quantil ));
            System.out.println(this.MedianType +"   " + quantil + "   "+listMax.size());
            countMedian = 0;
            globalList=null;
            globalList=new ArrayList<>();
            globalTDIgest =null;
            //globalTDIgest = new AVLTreeDigest(Costant.COMPRESSION);
            globalTDIgest = new MergingDigest(Costant.COMPRESSION);
        }
    }
}
