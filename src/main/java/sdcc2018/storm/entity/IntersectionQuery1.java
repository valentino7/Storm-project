package sdcc2018.storm.entity;

import java.util.Comparator;
import java.util.List;

public class IntersectionQuery1 extends Intersection implements Comparator<IntersectionQuery1>{

    private double VelocitaMedia;
    private int numeroVeicoli;

    public IntersectionQuery1() {
    }

    public IntersectionQuery1(double velocitaMedia, int numeroVeicoli) {
        VelocitaMedia = velocitaMedia;
        this.numeroVeicoli = numeroVeicoli;
    }

    public IntersectionQuery1(List<Sensor> l, int id,double velocitaMedia, int numeroVeicoli) {
        super(l,id);
        this.VelocitaMedia = velocitaMedia;
        this.numeroVeicoli = numeroVeicoli;
    }

    public IntersectionQuery1(List<Sensor> l, int id) {
        super(l,id);
    }

    public double getVelocitaMedia() {
        return VelocitaMedia;
    }

    public void setVelocitaMedia(double velocitaMedia) {
        VelocitaMedia = velocitaMedia;
    }

    public int getNumeroVeicoli() {
        return numeroVeicoli;
    }

    public void setNumeroVeicoli(int numeroVeicoli) {
        this.numeroVeicoli = numeroVeicoli;
    }


    @Override
    public String toString(){
        return "id="+this.getId()+" velMedia="+this.getVelocitaMedia();
    }


    @Override
    public int compare(IntersectionQuery1 intersection, IntersectionQuery1 t1) {
        if ( intersection.getVelocitaMedia() > t1.getVelocitaMedia() )
            return -1;
        else if ( intersection.getVelocitaMedia() < t1.getVelocitaMedia() )
            return 1;
        else
            return 0;
    }

}
