package sdcc2018.storm.entity;

import java.util.List;

public class IntersectionControl extends Intersection {

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
}
