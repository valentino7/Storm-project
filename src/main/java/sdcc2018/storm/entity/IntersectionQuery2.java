package sdcc2018.storm.entity;

import com.tdunning.math.stats.TDigest;

import java.util.List;

public class IntersectionQuery2 extends Intersection {

    private double medianaVeicoli;
    private TDigest td1;

    public IntersectionQuery2(double medianaVeicoli, TDigest td1) {
        this.medianaVeicoli = medianaVeicoli;
        this.td1 = td1;
    }

    public IntersectionQuery2(List<Sensor> l, int id, double velocitaMedia, int numeroVeicoli) {
        super(l,id);
        this.medianaVeicoli = medianaVeicoli;
        this.td1 = td1;
    }

    public IntersectionQuery2(List<Sensor> l, int id) {
        super (l,id);
    }

    public double getMedianaVeicoli() {
        return medianaVeicoli;
    }

    public void setMedianaVeicoli(double medianaVeicoli) {
        this.medianaVeicoli = medianaVeicoli;
    }

    public TDigest getTd1() {
        return td1;
    }

    public void setTd1(TDigest td1) {
        this.td1 = td1;
    }
}
