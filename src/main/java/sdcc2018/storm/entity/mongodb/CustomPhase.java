package sdcc2018.storm.entity.mongodb;

import sdcc2018.storm.entity.Costant;

import java.io.Serializable;
import java.util.Random;

public class CustomPhase implements Serializable {
    private int id;
    private int greenTime;
    private int redTime;

    public CustomPhase(){
    }
    public CustomPhase(int id, int greenTime, int redTime) {
        this.id = id;
        this.greenTime = greenTime;
        this.redTime = redTime;
    }
    public static CustomPhase[] randomPhase(){
        CustomPhase customPhase[]=new CustomPhase[2];
        Random rand=new Random();
        double greenTimeRandom=rand.nextDouble()*(Costant.CYCLE_TIME-Costant.CHANGE_TIME);
        double redTimeRandom=Costant.CYCLE_TIME-Costant.CHANGE_TIME-greenTimeRandom;
        customPhase[0]=new CustomPhase();
        customPhase[0].setId(0);
        customPhase[0].setGreenTime((int)greenTimeRandom);
        customPhase[0].setRedTime((int)redTimeRandom);
        customPhase[1]=new CustomPhase();
        customPhase[1].setId(1);
        customPhase[1].setGreenTime((int)redTimeRandom);
        customPhase[1].setRedTime((int)greenTimeRandom);
        return customPhase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    public int getRedTime() {
        return redTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }
    @Override
    public String toString(){
        return "id="+id+",greenTime="+greenTime+",redTime="+redTime;
    }
}
