package Model;

import Controller.Game;

import java.awt.*;
import java.util.ArrayList;

public class Eggs {

    ArrayList<Egg> eggs = new ArrayList<>();

    private ArrayList <MyPoint> locations = new ArrayList<>();

//    public void draw(Graphics g){
//
//        for (Egg egg: eggs){
//
//            egg.draw(g);
//
//        }
//
//
//    }

    public void move(int Delay){

        for (Egg egg: eggs){

            egg.move(Delay);

        }

        removeDead();

        addLocations();

    }

    public void removeDead(){

        ArrayList<Egg> dead = new ArrayList<>();

        for (Egg egg: eggs){


            if(Game.isOutOfScreen(egg.getLocation()) || !egg.isAlive()){

                dead.add(egg);

            }

        }

        for (Egg egg: dead) {

            eggs.remove(egg);

        }

    }

    private void addLocations(){

        locations.clear();

        for (Egg egg: eggs) {

            locations.add(egg.getLocation());

        }

    }

    public void add(Egg egg){

        eggs.add(egg);

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }

}
