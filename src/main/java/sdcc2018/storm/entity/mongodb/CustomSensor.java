package sdcc2018.storm.entity.mongodb;
import sdcc2018.storm.entity.Costant;

import java.io.Serializable;
public class CustomSensor implements Serializable {
    private int intersection;
    private int trafficLight;
    private double saturation;
    private double latitude;
    private double longitude;
    private String stateTrafficLight[];

    public String[] getStateTrafficLight() {
        return stateTrafficLight;
    }

    public void setStateTrafficLight(String[] stateTrafficLight) {
        this.stateTrafficLight = stateTrafficLight;
    }

    public CustomSensor(int intersection, int trafficLight, double saturation, double latitude, double longitude) {
        this.intersection = intersection;
        this.trafficLight = trafficLight;
        this.saturation = saturation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stateTrafficLight=new String[3];
        stateTrafficLight[0]=new String("OK");
        stateTrafficLight[1]=new String("OK");
        stateTrafficLight[2]=new String("OK");
    }
    public CustomSensor(){}
    public int getIntersection() {
        return intersection;
    }

    public void setIntersection(int intersection) {
        this.intersection = intersection;
    }

    public int getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(int trafficLight) {
        this.trafficLight = trafficLight;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLightToBroken(){
        if(stateTrafficLight[0].equals(Costant.OK)){
            stateTrafficLight[0]=Costant.KO;
            return;
        }
        if(stateTrafficLight[1].equals(Costant.OK)){
            stateTrafficLight[1]=Costant.KO;
            return;
        }
        if(stateTrafficLight[2].equals(Costant.OK)){
            stateTrafficLight[2]=Costant.KO;
            return;
        }
        return;
    }
    @Override
    public String toString(){
        return "id="+intersection+",idsem="+trafficLight+",saturation="+saturation+",latitude="+latitude+",longitude="+longitude+",light1="+stateTrafficLight[0]+",light2="+stateTrafficLight[1]+",light3="+stateTrafficLight[2];
    }
}
