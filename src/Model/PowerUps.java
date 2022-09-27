package Model;

import Controller.Game;

import java.awt.*;
import java.util.ArrayList;

public class PowerUps {

    ArrayList <PowerUp> powerUps = new ArrayList<>();

    private ArrayList <MyPoint> locations = new ArrayList<>();
    private ArrayList <String> type = new ArrayList<>();


//    public void draw(Graphics g){
//
//        for (PowerUp powerUp : powerUps){
//
//            powerUp.draw(g);
//
//        }
//
//
//    }

    public void move(int Delay){

        for (PowerUp powerUp : powerUps){

            powerUp.move(Delay);

        }

        removeDead();

        addLocations();

    }

    public void removeDead(){

        ArrayList<PowerUp> dead = new ArrayList<>();

        for (PowerUp powerUp : powerUps){


            if(Game.isOutOfScreen(powerUp.getLocation()) || !powerUp.isAlive()){

                dead.add(powerUp);

            }

        }

        for (PowerUp powerUp : dead) {

            powerUps.remove(powerUp);

        }

    }

    private void addLocations(){

        locations.clear();
        type.clear();

        for (PowerUp powerUp:powerUps) {

            locations.add(powerUp.getLocation());
            type.add(powerUp.powerUpType.getType());

        }

    }

    public void add(PowerUp powerUp){

        powerUps.add(powerUp);

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }

    public ArrayList<String> getType() {
        return type;
    }
}
