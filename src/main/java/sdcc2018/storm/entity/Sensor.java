package sdcc2018.storm.entity;


import java.io.Serializable;

public class Sensor implements Serializable {

    private int intersection;//intersezione di cui fa parte il sensore
    private int trafficLight;//va da 0 a 3,specifica quale semaforo è della specifica intersezione
    private double speed;//velocità media
    private int numVehicles;//numero veicoli
    private double saturation;//saturazione
    private double latitude;
    private double longitude;
    private String stateTrafficLight[];

    public Sensor(){
    }
    public Sensor(int i, int s, double vel, int nv, double saturation){
        this.intersection = i;
        this.trafficLight = s;
        this.speed = vel;
        this.numVehicles = nv;
        this.saturation=saturation;
    }
    public Sensor(int i, int s, double vel, int nv,double saturation,double latitude,double longitude,String[]stateTrafficLight){
        this.intersection = i;
        this.trafficLight = s;
        this.speed = vel;
        this.numVehicles = nv;
        this.saturation=saturation;
        this.latitude=latitude;
        this.longitude=longitude;
        this.stateTrafficLight=new String[3];
        this.stateTrafficLight[0]=stateTrafficLight[0];
        this.stateTrafficLight[1]=stateTrafficLight[1];
        this.stateTrafficLight[2]=stateTrafficLight[2];
    }

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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public void setNumVehicles(int numVehicles) {
        this.numVehicles = numVehicles;
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

    public String[] getStateTrafficLight() {
        return stateTrafficLight;
    }

    public void setStateTrafficLight(String[] stateTrafficLight) {
        this.stateTrafficLight = stateTrafficLight;
    }

    @Override
    public String toString(){
        return "velocità "+this.speed +" num="+this.numVehicles;
    }
    public boolean Broken(){
        if(!stateTrafficLight[0].equals(Costant.OK)){
            return true;
        }
        if(!stateTrafficLight[1].equals(Costant.OK)){
            return true;
        }
        if(!stateTrafficLight[2].equals(Costant.OK)){
            return true;
        }
        return false;
    }
}
