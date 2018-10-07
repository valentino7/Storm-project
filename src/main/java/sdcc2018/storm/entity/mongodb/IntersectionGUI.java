package sdcc2018.storm.entity.mongodb;

public class IntersectionGUI {
    private int idIntersection;
    private CustomSensor[] SensorList=new CustomSensor[4];
    private CustomPhase[] listPhase=new CustomPhase[2];

    public IntersectionGUI(int idIntersection,CustomSensor[] SensorList, CustomPhase[] listPhase) {
        this.idIntersection = idIntersection;
        this.SensorList = SensorList;
        this.listPhase = listPhase;
    }

    public int getIdIntersection() {
        return idIntersection;
    }

    public void setIdIntersection(int id) {
        this.idIntersection = id;
    }

    public CustomSensor[] getSensorList() {
        return SensorList;
    }

    public void setSensorList(CustomSensor[] listSens) {
        this.SensorList = listSens;
    }

    public CustomPhase[] getListPhase() {
        return listPhase;
    }

    public void setListPhase(CustomPhase[] listPhase) {
        this.listPhase = listPhase;
    }

    @Override
    public String toString(){
        return "id="+idIntersection+",sensors="+SensorList[0].toString()+
                ","+SensorList[1].toString()+","+SensorList[2].toString()+","+SensorList[3].toString()+",phases="+listPhase[0].toString()+","+listPhase[1].toString();
    }
}
