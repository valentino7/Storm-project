package sdcc2018.storm.entity;

public class TupleSensor extends Sensor {
    private double latitude;
    private double longitude;
    private String stateTrafficLight[];

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
}
