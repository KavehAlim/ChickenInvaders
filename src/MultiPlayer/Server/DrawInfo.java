package MultiPlayer.Server;

import Model.MyPoint;

import java.util.ArrayList;

public class DrawInfo {

    public boolean changing;
    public int waveNum;

    public boolean paused;

    public boolean end;

    public ArrayList<ShipDrawInfo> shipsInfo;

    public ArrayList<MyPoint> chickenLocations;
    public ArrayList<Integer> chickenImageNumber;

    public ArrayList<MyPoint> coinLocations;
    public ArrayList<Integer> coinImageNumber;

    public ArrayList<MyPoint> powerUpLocations;
    public ArrayList<String> powerUpType;

    public ArrayList<MyPoint> eggLocations;


    public DrawInfo(){

        shipsInfo = new ArrayList<>();

        chickenLocations = new ArrayList<>();
        chickenImageNumber = new ArrayList<>();

        coinLocations = new ArrayList<>();
        coinImageNumber = new ArrayList<>();

        powerUpLocations = new ArrayList<>();
        powerUpType = new ArrayList<>();

        eggLocations = new ArrayList<>();

    }

    public void setDrawInfo(DrawInfo info){

        shipsInfo.clear();
        for (ShipDrawInfo shipInfo:info.shipsInfo) {

            ShipDrawInfo shipDrawInfo = new ShipDrawInfo();
            shipDrawInfo.setShipDrawInfo(shipInfo);
            shipsInfo.add(shipInfo);

        }

        chickenLocations.clear();
        chickenImageNumber.clear();
        for (MyPoint chickenLocation:info.chickenLocations){

            MyPoint location = new MyPoint(chickenLocation.x,chickenLocation.y);
            chickenLocations.add(location);
            chickenImageNumber.add(info.chickenImageNumber.get(info.chickenLocations.indexOf(chickenLocation)));

        }

        coinLocations.clear();
        coinImageNumber.clear();
        for (MyPoint coinLocation:info.coinLocations){

            MyPoint location = new MyPoint(coinLocation.x,coinLocation.y);
            coinLocations.add(location);
            coinImageNumber.add(info.coinImageNumber.get(info.coinLocations.indexOf(coinLocation)));

        }

        powerUpLocations.clear();
        powerUpType.clear();
        for (MyPoint powerUpLocation:info.powerUpLocations){

            MyPoint location = new MyPoint(powerUpLocation.x,powerUpLocation.y);
            powerUpLocations.add(location);
            powerUpType.add(info.powerUpType.get(info.powerUpLocations.indexOf(powerUpLocation)));

        }

        eggLocations.clear();
        for (MyPoint eggLocation:info.eggLocations){

            MyPoint location = new MyPoint(eggLocation.x,eggLocation.y);
            eggLocations.add(location);

        }

        changing = info.changing;
        waveNum = info.waveNum;

    }

}
