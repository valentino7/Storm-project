package sdcc2018.storm.entity.mongodb;

import sdcc2018.storm.entity.Costant;

import java.io.Serializable;

public class StateSensor implements Serializable {
    private int idIntersection;
    private int idTrafficLight;
    private String stateTrafficLight[];
    public StateSensor(){

    }
    public StateSensor(int idIntersection, int idTrafficLight){
        this.idIntersection=idIntersection;
        this.idTrafficLight=idTrafficLight;
        this.stateTrafficLight=new String[3];
        stateTrafficLight[0]=new String("OK");
        stateTrafficLight[1]=new String("OK");
        stateTrafficLight[2]=new String("OK");
    }

    public int getIdIntersection() {
        return idIntersection;
    }

    public void setIdIntersection(int idIntersection) {
        this.idIntersection = idIntersection;
    }

    public int getIdTrafficLight() {
        return idTrafficLight;
    }

    public void setIdTrafficLight(int idTrafficLight) {
        this.idTrafficLight = idTrafficLight;
    }

    public String[] getStateTrafficLight() {
        return stateTrafficLight;
    }

    public void setStateTrafficLight(String[] stateTrafficLight) {
        this.stateTrafficLight = stateTrafficLight;
    }
    public void setLightToBroken() {
        if (stateTrafficLight[0].equals(Costant.OK)) {
            stateTrafficLight[0] = Costant.KO;
            return;
        }
        if (stateTrafficLight[1].equals(Costant.OK)) {
            stateTrafficLight[1] = Costant.KO;
            return;
        }
        if (stateTrafficLight[2].equals(Costant.OK)) {
            stateTrafficLight[2] = Costant.KO;
            return;
        }
        return;
    }
    @Override
    public String toString(){
        return "idIntersection="+idIntersection+",idTrafficLight="+idTrafficLight+",stateTrafficLight="+stateTrafficLight;
    }
}
