package Model;

import java.util.ArrayList;

public abstract class ChickenGroup {

    public ArrayList<Chicken> chickens;
    int number;
    Coins coins;
    PowerUps powerUps;
    Eggs eggs;
    MyPoint velocity;
    int playerNum;
    int level;

    static final MyPoint startPoint = new MyPoint(420.0,60.0);
    static final MyPoint endPoint = new MyPoint(1240.0,540.0);

    ChickenGroup(Integer playerNum, Integer level, Integer number, Coins coins,PowerUps powerUps, Eggs eggs){

        velocity = new MyPoint(0.0 , 0.0);
        this.playerNum = playerNum;
        this.level = level;
        this.eggs = eggs;
        this.powerUps = powerUps;
        this.coins = coins;
        this.number = number;
        chickens = new ArrayList<>();

    }

    public void toDoConstantly(){

        setVelocity();
//        reShape();

    }
    protected void add(Chicken chicken){

        chickens.add(chicken);

    }

    public void reShape(){

        removeDead();

    }

    public void setVelocity(){


    }

    public void setChickens(){

    }

    private void removeDead(){

        ArrayList<Chicken> dead = new ArrayList<>();

        for (Chicken chicken: chickens){


            if(!chicken.isAlive()){

                dead.add(chicken);

            }

        }

        for (Chicken chicken: dead) {

            chickens.remove(chicken);

        }

    }
}
