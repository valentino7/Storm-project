package sdcc2018.storm.entity;

import java.io.Serializable;
import java.util.List;

public abstract class Intersection implements Serializable {

    private List<Sensor> l;
    private int id;


    public Intersection() {
    }

    public Intersection(List<Sensor> l, int id) {
        this.l = l;
        this.id = id;
    }

    public List<Sensor> getL() {
        return l;
    }

    public void setL(List<Sensor> l) {
        this.l = l;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
