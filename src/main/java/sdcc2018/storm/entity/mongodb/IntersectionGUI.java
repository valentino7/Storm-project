package sdcc2018.storm.entity.mongodb;

import java.util.List;

public class IntersectionGUI {
    private int id;
    private CustomSensor[] listSens=new CustomSensor[4];
    private CustomPhase[] listPhase=new CustomPhase[2];

    public IntersectionGUI(CustomSensor[] listSens, int id, CustomPhase[] listPhase) {
        this.listSens = listSens;
        this.id = id;
        this.listPhase = listPhase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomSensor[] getListSens() {
        return listSens;
    }

    public void setListSens(CustomSensor[] listSens) {
        this.listSens = listSens;
    }

    public CustomPhase[] getListPhase() {
        return listPhase;
    }

    public void setListPhase(CustomPhase[] listPhase) {
        this.listPhase = listPhase;
    }
    @Override
    public String toString(){
        return "id="+id+",phases="+listPhase[0].toString()+","+listPhase[1].toString()+",sensors="+listSens[0].toString()+
                ","+listSens[1].toString()+","+listSens[2].toString()+","+listSens[3].toString();
    }
}
