package sdcc2018.storm.query1.bolt;

import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionQuery1;
import org.apache.storm.shade.org.apache.commons.collections.ListUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalRankBolt extends BaseBasicBolt {
    private int countIntermediateRank = 0;
    private List<IntersectionQuery1> globalRanking = new ArrayList<>();
    private String AvgType;
    private int repNum;


    public GlobalRankBolt(String avgBolt, int rep) {
        this.AvgType = avgBolt;
        this.repNum = rep;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(Costant.ID_WINDOW,Costant.RANK_TOPK));
    }


    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        List<IntersectionQuery1> list = ( List<IntersectionQuery1> ) tuple.getValueByField(Costant.PARTIAL_RANK);
        countIntermediateRank++;
        if(globalRanking.isEmpty()) {
            globalRanking = list;
        }else{
            sortOrderedRank(list);
        }
        if(countIntermediateRank >= repNum) {
            collector.emit(new Values(this.AvgType,globalRanking));
            System.out.println(this.AvgType + "    "+  globalRanking.size() +"    "+ globalRanking);
            globalRanking = null;
            globalRanking = new ArrayList<>();
            countIntermediateRank = 0;
        }

    }

    private void sortOrderedRank( List<IntersectionQuery1> list) {
        if(globalRanking.size() < Costant.TOP_K) {
            //sorting e sublist10
            globalRanking=unionAndSort(globalRanking,list);
        }else if(list.get(0).getVelocitaMedia() <= globalRanking.get(Costant.TOP_K-1).getVelocitaMedia()){
                globalRanking=unionAndSort(globalRanking,list);
        }
    }

    private List<IntersectionQuery1> unionAndSort(List<IntersectionQuery1> list1, List<IntersectionQuery1> list2){
        List<IntersectionQuery1> l = ListUtils.union(list1,list2);
        Collections.sort(l,new IntersectionQuery1());
        List<IntersectionQuery1> l2 = null;
        if(l.size()> Costant.TOP_K) {
            l2 = new ArrayList<>(l.subList(0, Costant.TOP_K));
            return l2;
        }
        else{
            return l;
        }

    }


}
