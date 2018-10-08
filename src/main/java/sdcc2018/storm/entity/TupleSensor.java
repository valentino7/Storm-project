package sdcc2018.storm.entity;

public class TupleSensor extends Sensor {
    private double latitude;
    private double longitude;
    private String stateTrafficLight[];

    public TupleSensor(){
    }

    public TupleSensor(double latitude,double longitude,String stateTrafficLight[]){
        super();
        this.latitude=latitude;
        this.longitude=longitude;
        this.stateTrafficLight=stateTrafficLight;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    @Override
    public String toString(){
        return "";
    }
}
