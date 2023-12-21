package HeigCorp.app.model;

public class Kirby {
    private String kirbyName;

    private int hunger;

    private int funPoint;

    private int swagPoint;

    private String fileImagePath;

    Kirby(String name, String imagePath){
        this.swagPoint = 100;
        this.hunger = 0;
        this.funPoint = 100;
        this.kirbyName = name;
        this. fileImagePath = imagePath;
    }

    int getHunger(){
        return hunger;
    }

    int getFunPoint(){
        return funPoint;
    }

    int getSwagPoint(){
        return swagPoint;
    }

    String getName(){
        return kirbyName;
    }
}
