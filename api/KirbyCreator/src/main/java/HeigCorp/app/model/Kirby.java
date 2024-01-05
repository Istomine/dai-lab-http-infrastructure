package HeigCorp.app.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Timer;

public class Kirby {

    Instant lastUpdated;
    private String kirbyName;

    private int hunger;

    private int funPoint;

    private int swagPoint;

    private String fileImagePath;

    public Kirby(String name, String imagePath){
        this.lastUpdated = Instant.now();
        this.swagPoint = 0;
        this.hunger = 100;
        this.funPoint = 100;
        this.kirbyName = name;
        this.fileImagePath = imagePath;
    }

    public int getHunger(){
        return hunger;
    }

    public int getFunPoint(){
        return funPoint;
    }

    public int getSwagPoint(){
        return swagPoint;
    }

    public String getName(){
        return kirbyName;
    }

    public String getImage(){
        return fileImagePath;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setHunger(int hunger){
        this.hunger = hunger;
    }

    public void setSwagPoint(int swagPoint){
        this.swagPoint = swagPoint;
    }

    public void setFunPoint(int funPoint){
        this.funPoint = funPoint;
    }
}
