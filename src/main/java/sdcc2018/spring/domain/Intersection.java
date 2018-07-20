package sdcc2018.spring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import com.tdunning.math.stats.TDigest;
import org.springframework.stereotype.Indexed;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
@Document(collection = "Intersection")
public class Intersection implements Comparator<Intersection> {


    @Id
    private String id;

    @Field("idIntersection")
    private int idIntersection;

    @Field("meanSpeed")
    private double VelocitaMedia;

    @Field("medianVehicles")
    private double medianaVeicoli;

    @Field("numVehicles")
    private int numeroVeicoli;

    @Field("semaphoreList")
    private List<Semaphore> semaphoreList;

    @Field("phaseList")
    private List<Phase> phases;

    private TDigest td1;


    /*public Intersection(List<Semaphore> l, int id) {
        this.l = l;
        this.id = id;
        this.td1= new AVLTreeDigest(Costant.COMPRESSION);
    }*/

    public List<Semaphore> getL() {
        return this.semaphoreList;
    }

    public void setL(List<Semaphore> l) {
        this.semaphoreList = l;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString(){
        return "id="+this.id+" velMedia="+this.VelocitaMedia;
    }

    @Override
    public int compare(Intersection intersection, Intersection t1) {
        if ( intersection.getVelocitaMedia() > t1.getVelocitaMedia() )
            return -1;
        else if ( intersection.getVelocitaMedia() < t1.getVelocitaMedia() )
            return 1;
        else
            return 0;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }
}
