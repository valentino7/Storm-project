package sdcc2018.storm.entity;

import java.io.Serializable;
import java.util.List;

public class IntersectionControl extends Intersection implements Serializable {

    private List<Phase> phases;

    public IntersectionControl(List<Phase> phases) {
        this.phases = phases;
    }
    public IntersectionControl(List<Sensor> l, int id) {
        super(l,id);
    }


    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }


    @Override
    public String toString(){
        return "verdeFase1 : "+this.getPhases().get(0).getGreen() + " rossoFase1 : "+this.getPhases().get(0).getRed()
        + "\n verdeFase2 : "+this.getPhases().get(1).getGreen() + " rossoFase2 : "+this.getPhases().get(1).getRed();
    }


}
