package Model;

import Controller.Game;

import java.awt.*;
import java.util.ArrayList;

public class Coins {
    ArrayList<Coin> coins = new ArrayList<>();

    private ArrayList<MyPoint> locations = new ArrayList<>();
    private ArrayList<Integer> imageNumbers = new ArrayList<>();

//    public void draw(Graphics g){
//
//        for (Coin coin : coins){
//
//            coin.draw(g);
//
//        }
//
//
//    }

    public void move(int Delay){

        for (Coin coin : coins){

            coin.move(Delay);

        }

        removeDead();

        addLocations();

    }

    public void removeDead(){

        ArrayList<Coin> dead = new ArrayList<>();

        for (Coin coin : coins){


            if(Game.isOutOfScreen(coin.getLocation()) || !coin.isAlive()){

                dead.add(coin);

            }

        }

        for (Coin coin : dead) {

            coins.remove(coin);

        }

    }

    private void addLocations(){

        locations.clear();
        imageNumbers.clear();

        for (Coin coin: coins) {

            locations.add(coin.getLocation());
            imageNumbers.add(coin.getCurrentImageNumber());

        }

    }


    public void add(Coin coin){

        coins.add(coin);

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }

    public ArrayList<Integer> getImageNumbers() {
        return imageNumbers;
    }
}
