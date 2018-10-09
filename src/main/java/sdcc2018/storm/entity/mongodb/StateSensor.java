package sdcc2018.storm.entity.mongodb;

import sdcc2018.storm.entity.Costant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StateSensor implements Serializable {
    private int idIntersection;
    private int idTrafficLight;
    private List<String> stateTrafficLight=new ArrayList<>();
    public StateSensor(){

    }
    public StateSensor(int idIntersection, int idTrafficLight){
        this.idIntersection=idIntersection;
        this.idTrafficLight=idTrafficLight;
        this.stateTrafficLight.add(Costant.OK);
        this.stateTrafficLight.add(Costant.OK);
        this.stateTrafficLight.add(Costant.OK);
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

    public List<String> getStateTrafficLight() {
        return stateTrafficLight;
    }

    public void setStateTrafficLight(List<String> stateTrafficLight) {
        this.stateTrafficLight = stateTrafficLight;
    }

    public void setLightToBroken() {
        for(int i=0;i<3;i++){
            if(stateTrafficLight.get(i).equals(Costant.OK)){
                stateTrafficLight.set(i,Costant.KO);
                return;
            }
        }
        return;
    }
    @Override
    public String toString(){
        return "idIntersection="+idIntersection+",idTrafficLight="+idTrafficLight+",stateTrafficLight="+stateTrafficLight;
    }
}
