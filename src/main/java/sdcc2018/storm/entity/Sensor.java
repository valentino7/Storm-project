package sdcc2018.storm.entity;


import java.io.Serializable;

public class Sensor implements Serializable {

    private int intersection;//intersezione di cui fa parte il sensore
    private int trafficLight;//va da 0 a 3,specifica quale semaforo è della specifica intersezione
    private double speed;//velocità media
    private int numVehicles;//numero veicoli
    private double saturation;//saturazione

    public Sensor(int i, int s, double vel, int nv, double saturation){
        this.intersection = i;
        this.trafficLight = s;
        this.speed = vel;
        this.numVehicles = nv;
        this.saturation=saturation;
    }

    public Sensor(){
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


    @Override
    public String toString(){
        return "velocità "+this.speed +" num="+this.numVehicles;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }
}
