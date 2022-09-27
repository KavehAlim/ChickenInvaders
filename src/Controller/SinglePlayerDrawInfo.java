package Controller;

import Model.MyPoint;

import java.util.ArrayList;

public class SinglePlayerDrawInfo {

    public int waveNum;
    public boolean changing;

    public double temperature;
    public int coinNumber;
    public int shipLife;
    public int bombNumber;
    public long points;
    public String color = "Green";

    public MyPoint shipLocation;
    public boolean hit;

    public ArrayList<MyPoint> chickenLocations;
    public ArrayList<Integer> chickenImageNumber;

    public ArrayList<MyPoint> bulletLocations;
    public ArrayList<String> bulletType;

    public ArrayList<MyPoint> coinLocations;
    public ArrayList<Integer> coinImageNumber;

    public ArrayList<MyPoint> powerUpLocations;
    public ArrayList<String> powerUpType;

    public ArrayList<MyPoint> eggLocations;

    public ArrayList<MyPoint> missileLocations;


    SinglePlayerDrawInfo(){

        chickenLocations = new ArrayList<>();
        chickenImageNumber = new ArrayList<>();

        bulletLocations = new ArrayList<>();
        bulletType = new ArrayList<>();

        coinLocations = new ArrayList<>();
        coinImageNumber = new ArrayList<>();

        powerUpLocations = new ArrayList<>();
        powerUpType = new ArrayList<>();

        eggLocations = new ArrayList<>();

        missileLocations = new ArrayList<>();
    }

}
