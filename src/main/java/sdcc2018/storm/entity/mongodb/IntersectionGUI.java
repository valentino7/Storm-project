package sdcc2018.storm.entity.mongodb;

import java.util.List;

public class IntersectionGUI {
    private List<CustomSensor> listSens;
    private int id;
    private List<CustomPhase>listPhase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CustomPhase> getListPhase() {
        return listPhase;
    }

    public void setListPhase(List<CustomPhase> listPhase) {
        this.listPhase = listPhase;
    }

    public List<CustomSensor> getListSens() {
        return listSens;
    }

    public void setListSens(List<CustomSensor> listSens) {
        this.listSens = listSens;
    }
}
