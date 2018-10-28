package sdcc2018.storm.entity.mongodb;


import java.io.Serializable;
public class CustomSensor implements Serializable {
    private int intersection;
    private int trafficLight;
    private double saturation;
    private double latitude;
    private double longitude;

    public CustomSensor(int intersection, int trafficLight, double saturation, double latitude, double longitude) {
        this.intersection = intersection;
        this.trafficLight = trafficLight;
        this.saturation = saturation;
        this.latitude = latitude;
        this.longitude = longitude;

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
    @Override
    public String toString(){
        return "intersection="+intersection+",trafficlight="+trafficLight+",saturatin="+saturation+",latitude="+latitude+",longitude="+longitude;
    }
}
