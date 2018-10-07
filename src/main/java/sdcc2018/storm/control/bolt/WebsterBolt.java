package sdcc2018.storm.control.bolt;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.IntersectionControl;
import sdcc2018.storm.entity.Phase;
import sdcc2018.storm.entity.Sensor;


import java.util.ArrayList;
import java.util.List;

public class WebsterBolt extends BaseBasicBolt {
    private void webster(IntersectionControl i){
        List<Sensor> list_s=i.getL();
        // ordinare lista list_s;
        Phase p1 = new Phase(2);
        p1.setRatioFlowSaturation(  calculateMax(list_s,0,2) );
        Phase p2 = new Phase(1);
        p2.setRatioFlowSaturation( calculateMax(list_s,1,3) );

        p1.setEffective_green(calculateEffectiveGreen(p1.getRatioFlowSaturation(),p2.getRatioFlowSaturation()));
        p2.setEffective_green(calculateEffectiveGreen(p2.getRatioFlowSaturation(),p1.getRatioFlowSaturation()));

        p1.setEffective_red(Costant.CYCLE_TIME - p1.getEffective_green());
        p2.setEffective_red(Costant.CYCLE_TIME - p2.getEffective_green());

        p1.setGreen(calculateGreen(p1.getEffective_green()));
        p2.setGreen(calculateGreen(p2.getEffective_green()));

        p1.setRed(calculateRed(p1.getGreen()));
        p2.setRed(calculateRed(p2.getGreen()));

        List<Phase> phases = new ArrayList<>();
        phases.add(p1);
        phases.add(p2);
        i.setPhases(phases);


    }

    private double calculateMax(List<Sensor>list_s,int i,int j){
        return Math.max(list_s.get(i).getNumVehicles()/ list_s.get(i).getSaturation() ,list_s.get(j).getNumVehicles() / list_s.get(j).getSaturation());
    }

    private double calculateEffectiveGreen( double FirstRatio, double SecondRatio){
        int value = Costant.CYCLE_TIME - ( Costant.NUM_PHASE* Costant.LOST_TIME);
        return ( FirstRatio/ ( FirstRatio + SecondRatio) )* value;
    }

    private int calculateGreen(double effectiveGreen){
        return (int) Math.round(effectiveGreen + Costant.LOST_TIME - Costant.CHANGE_TIME);
    }

    private int calculateRed(int green){
        return Costant.CYCLE_TIME - Costant.CHANGE_TIME - green;
    }


    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        for(IntersectionControl i:(ArrayList<IntersectionControl>)input.getValueByField(Costant.MAP_INTERSECTION)){
            webster(i);
        }
        return;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields( Costant.PHASE));
    }
}
