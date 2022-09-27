package MultiPlayer.Server;


import Controller.Game;
import Controller.GameStatus;
import Controller.JSON;
import Controller.Player;
import Model.*;
import MultiPlayer.Client.ClientStatus;
import Reflection.ExtensionLoader;


import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

public class MultiPlayerGame {

    public final static Dimension FullScreen = new Dimension(1680, 975);

    ArrayList<Ship> ships;
//    private Mission mission;
    private transient ArrayList<Class> chickensClasses = new ArrayList<>();
    private transient ArrayList<Class> bossClasses = new ArrayList<>();
    private transient ChickenGroup currentWave;
    private int playerNum;
    private int waveNum;
    private int level;
    boolean changingWaves;
    private long changeTime;
    private Eggs eggs;
    private Coins coins;
    private PowerUps powerUps;
    private boolean levelCompleted;
    ArrayList <ClientStatus> status;
    DrawInfo drawInfo;
    private Enemies enemies;
    boolean paused;
    private int missions;

    MultiPlayerGame(int playerNum, int missionNumber) {

        this.playerNum = playerNum;
        paused = false;
        missions = missionNumber;
        setChickensClasses();
        setBossClasses();
        drawInfo = new DrawInfo();
        ships = new ArrayList<>();
        status = new ArrayList<>();
        eggs = new Eggs();
        coins = new Coins();
        powerUps = new PowerUps();
        enemies = new Enemies();
//        mission = new Mission(1,enemies,eggs,powerUps,coins);

    }

    private void setChickensClasses(){
        Class circleGroupClass = CircleGroup.class;
        Class rectangleGroupClass = RectangleGroup.class;
        Class rotatingGroupClass = RotatingGroup.class;
        Class aggressiveGroupClass = AggressiveGroup.class;
        chickensClasses.add(circleGroupClass);
        chickensClasses.add(rectangleGroupClass);
        chickensClasses.add(rotatingGroupClass);
        chickensClasses.add(aggressiveGroupClass);
    }

    private void setBossClasses() {
        Class boss = Boss.class;
        bossClasses.add(boss);
    }

    public void draw(){

        boolean end = true;

        for (Ship ship: ships) {

            end = end && ship.isDead();
            int index = ships.indexOf(ship);
            ShipDrawInfo shipInfo = drawInfo.shipsInfo.get(index);
            shipInfo.shipLocation = ship.currentLocation;
            shipInfo.hit = ship.isHit();

            shipInfo.bombNumber = ship.getBombNumber();
            shipInfo.temperature = ship.weapon.getTemperature();
            shipInfo.shipLife = ship.getLifeNumber();
            shipInfo.coinNumber = ship.getCoinNum();
            shipInfo.points = ship.getPoints();
            shipInfo.missileLocations = ship.missilery.missiles.getLocations();
            shipInfo.bulletLocations = ship.weapon.bullets.getLocations();
            shipInfo.bulletType = ship.weapon.bullets.getType();

            shipInfo.id = status.get(index).id;
            drawInfo.shipsInfo.set(index,shipInfo);

        }

        if(ships.size() != 0 && end){

            drawInfo.end = true;

        }

        drawInfo.chickenLocations = enemies.getLocations();
        drawInfo.chickenImageNumber = enemies.getImageNumbers();

        drawInfo.coinLocations = coins.getLocations();
        drawInfo.coinImageNumber = coins.getImageNumbers();


        drawInfo.eggLocations = eggs.getLocations();


        drawInfo.powerUpLocations = powerUps.getLocations();
        drawInfo.powerUpType = powerUps.getType();

        drawInfo.changing = changingWaves;
        drawInfo.waveNum = waveNum;


    }

    public void move(int Time) {

        for (Ship ship : ships) {

            int index = ships.indexOf(ship);

            ship.doConstantly(Time, status.get(index).mouseStatus, enemies, eggs, coins, powerUps);

            for (Ship Ship:ships) {

                if(!ship.equals(Ship)) {

                    for (Bullet bullet : Ship.weapon.bullets.bullets) {

                        if (Game.intersect(ship.currentLocation, Model.Ship.SIZE, bullet.getLocation(), bullet.SIZE) && !ship.isHit()) {

                            ship.die();
                            bullet.die();

                        }

                    }

                }

            }

        }



        enemies.move(Time);
        eggs.move(Time);
        coins.move(Time);
        powerUps.move(Time);

        waveHandler();
//        mission.update();


    }

    private void waveHandler() {

//        mission.update();

        if(waveNum > missions * 5/* || ship.getLifeNumber() <=0*/){

            drawInfo.end = true;

        }

        if(currentWave != null) {
            currentWave.toDoConstantly();
        }

//        normalWaveHandler();
        reflectionWaveHandler();
    }

    private void normalWaveHandler() {
        if(enemies.enemies.size() == 0) {
            if(waveNum % 5 == 0){

                level ++;
                levelFinished();

            }
            if(!changingWaves) {
                level = waveNum / 5 + 1;
                waveNum++;
                changeTime = System.currentTimeMillis();
                changingWaves = true;
            }
            if(changingWaves && System.currentTimeMillis() > changeTime + 3 * 1000) {

                if(waveNum % 5 == 1)
                    levelCompleted = false;
                currentWave = new RectangleGroup(playerNum,level,40, coins, powerUps, eggs);
                if(waveNum % 5 == 2)
                    currentWave = new CircleGroup(playerNum, level, 20 , coins, powerUps, eggs);
                if(waveNum % 5 == 3)
                    currentWave = new RotatingGroup(playerNum,level,20, coins, powerUps, eggs);
                if(waveNum % 5 == 4)
                    currentWave = new AggressiveGroup(playerNum,level,20, coins, powerUps, eggs);

                if(waveNum % 5 > 0) {

                    for (Enemy enemy : currentWave.chickens) {

                        enemies.add(enemy);

                    }

                }

                if(waveNum % 5 == 0){

                    enemies.add(new Boss(level, eggs,powerUps, coins));
                }


                changingWaves = false;
            }
        }
    }

    private void levelFinished() {
        levelCompleted = true;
        for(Ship ship: ships)
        ship.levelEnd();
    }

    private void reflectionWaveHandler() {

        if (enemies.enemies.size() == 0) {

            if(waveNum % 5 == 0 && !changingWaves && level != 0){
                level ++;
                levelFinished();
            }
            if(!changingWaves) {
                level = waveNum / 5 + 1;
                waveNum++;
                changeTime = System.currentTimeMillis();
                changingWaves = true;
            }

            if (changingWaves && System.currentTimeMillis() > changeTime + 3 * 1000) {

                if (waveNum % 5 > 0) {

                    int index = /*new Random().nextInt(chickensClasses.size())*/chickensClasses.size() - 1;
                    Class chickens = chickensClasses.get(index);
                    Constructor constructor;
                    try {
                        constructor = chickens.getConstructor(Integer.class,Integer.class,Integer.class, Coins.class, PowerUps.class, Eggs.class);
                        currentWave = (ChickenGroup) (constructor.newInstance(ships.size()/*playerNum*/,(level + 3) % 4 + 1,new Random().nextInt(10) + 10,coins,powerUps, eggs));
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    for (Enemy enemy : currentWave.chickens) {

                        enemies.add(enemy);

                    }

                }

                if (waveNum % 5 == 0) {

                    int index = /*new Random().nextInt(bossClasses.size()) */bossClasses.size() - 1;
                    Class boss = bossClasses.get(index);
                    Constructor constructor = null;
                    try {
                        constructor = boss.getConstructor(Integer.class, Eggs.class, PowerUps.class, Coins.class);
                        enemies.add((Boss) (constructor.newInstance((level + 3) % 4 + 1, eggs, powerUps, coins)));
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }


                changingWaves = false;
            }
        }

    }


    void addPlayer(ClientStatus status){

        Ship ship = new Ship();
        this.status.add(status);
        drawInfo.shipsInfo.add(new ShipDrawInfo());
        ships.add(ship);


    }

    void removePlayer(int id){

        int index = -1;

        for (ClientStatus clientStatus: status) {

            if(clientStatus.id == id) {
                index = status.indexOf(clientStatus);
            }


        }

        if(index != -1) {
            this.status.remove(index);
            ships.remove(index);
            drawInfo.shipsInfo.remove(index);
        }

    }


    void addBoss(String className){

        ExtensionLoader<Boss> bossExtensionLoader = new ExtensionLoader<Boss>();

        Class newBoss = null;
        try {
            newBoss = bossExtensionLoader.load("Reflect/", className, Boss.class);
        } catch (ClassNotFoundException | MalformedURLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        bossClasses.add(newBoss);

    }

    void addChickens(String className){

        ExtensionLoader<ChickenGroup> chickens = new ExtensionLoader<ChickenGroup>();

        Class newChickens = null;
        try {
            newChickens = chickens.load("Reflect/", className, ChickenGroup.class);
        } catch (ClassNotFoundException | MalformedURLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        chickensClasses.add(newChickens);
    }
}
