package sdcc2018.storm.entity.mongodb;

public class CustomSensor {
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
        return "id="+intersection+",idsem="+trafficLight+",saturation="+saturation+",latitude="+latitude+",longitude="+longitude+",light1="+stateTrafficLight[0]+",light2="+stateTrafficLight[1]+",light3="+stateTrafficLight[2];
    }
}
