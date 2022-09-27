package Model;

import Controller.Game;

import java.awt.*;
import java.util.ArrayList;

public class Bullets {

    public ArrayList <Bullet> bullets = new ArrayList<>();

    private ArrayList <MyPoint> locations = new ArrayList<>();
    private ArrayList <String> type = new ArrayList<>();

//    public void draw(Graphics g){
//
//        for (Bullet Bullet : bullets){
//
//            Bullet.draw(g);
//
//        }
//
//
//    }

    public void move(int Delay){

        for (Bullet Bullet : bullets){

            Bullet.move(Delay);

        }

        removeDead();

        addLocations();

    }

    private void removeDead(){

        ArrayList<Bullet> dead = new ArrayList<>();

        for (Bullet bullet : bullets){


            if(Game.isOutOfScreen(bullet.location) || !bullet.isAlive()){

                dead.add(bullet);

            }

        }

        for (Bullet bullet:dead) {

            bullets.remove(bullet);

        }

    }

    private void addLocations(){

        locations.clear();
        type.clear();

        for (Bullet bullet: bullets) {

            locations.add(bullet.getLocation());
            type.add(bullet.bulletType.getType());

        }

    }

    public void add(Bullet bullet){

        bullets.add(bullet);

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }

    public ArrayList<String> getType() {
        return type;
    }
}
