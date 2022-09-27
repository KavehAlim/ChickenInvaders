package MultiPlayer.Server;

import Model.MyPoint;

import java.util.ArrayList;

public class ShipDrawInfo {

    public int id;
    public double temperature;
    public int coinNumber;
    public int shipLife;
    public int bombNumber;
    public int points;

    public MyPoint shipLocation;
    public boolean hit;

    public ArrayList<MyPoint> bulletLocations;
    public ArrayList<String> bulletType;

    public ArrayList<MyPoint> missileLocations;

    public ShipDrawInfo(){

        shipLocation = new MyPoint(800.0 , 900.0);
        bulletLocations = new ArrayList<>();
        bulletType = new ArrayList<>();
        missileLocations = new ArrayList<>();

    }

    public void setShipDrawInfo(ShipDrawInfo shipDrawInfo){

        if(shipDrawInfo == null) {

            return;

        }

        shipLocation.x = shipDrawInfo.shipLocation.x;
        shipLocation.y = shipDrawInfo.shipLocation.y;

        id = shipDrawInfo.id;

        temperature = shipDrawInfo.temperature;
        coinNumber = shipDrawInfo.coinNumber;
        shipLife = shipDrawInfo.shipLife;
        bombNumber = shipDrawInfo.bombNumber;
        points = shipDrawInfo.points;

        bulletLocations.clear();
        bulletType.clear();
        for (MyPoint bulletLocation:shipDrawInfo.bulletLocations){

            MyPoint location = new MyPoint(bulletLocation.x,bulletLocation.y);
            bulletLocations.add(location);
            bulletType.add(shipDrawInfo.bulletType.get(shipDrawInfo.bulletLocations.indexOf(bulletLocation)));

        }

        missileLocations.clear();
        for (MyPoint missileLocation:shipDrawInfo.missileLocations){

            MyPoint location = new MyPoint(missileLocation.x,missileLocation.y);
            missileLocations.add(location);

        }

        this.hit = shipDrawInfo.hit;


    }

}
