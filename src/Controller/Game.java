package Controller;

import DataBase.PlayerDataBase;
import Model.*;
import Reflection.ExtensionLoader;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import Menu.ScoreList;

import static View.Sound.playSound;

public class Game {

    public final static Dimension FullScreen = new Dimension(1680, 975);

    private transient ArrayList<Class> chickensClasses = new ArrayList<>();
    private transient ArrayList<Class> bossClasses = new ArrayList<>();

    private String name;
    private Ship ship;
    private double playingTime;
//    private Mission mission;
    private int waveNum;
    private int level;
    private boolean changingWaves;
    private long changeTime;
    private transient ChickenGroup currentWave;
    private Eggs eggs;
    private Coins coins;
    private PowerUps powerUps;
    private MouseStatus mouseStatus;
    private SinglePlayerDrawInfo drawInfo;
    private Enemies enemies;
    private transient View view;

    public Game(String name){

        setChickensClasses();
        setBossClasses();

        this.name = name;
        drawInfo = new SinglePlayerDrawInfo();
        mouseStatus = new MouseStatus();
        ship = new Ship();
        eggs = new Eggs();
        coins = new Coins();
        powerUps = new PowerUps();
        enemies = new Enemies();


    }

    public Game(JFrame frame, Player player) {

        setChickensClasses();
        setBossClasses();

        this.name = player.getName();
        drawInfo = new SinglePlayerDrawInfo();
        mouseStatus = new MouseStatus();
        ship = new Ship();
        eggs = new Eggs();
        coins = new Coins();
        powerUps = new PowerUps();
        enemies = new Enemies();
//        changingWaves = true;
//        mission = new Mission(1,enemies,eggs,powerUps,coins);
        view = new View(this , frame);


    }


    public void draw(){

        drawInfo.shipLocation = ship.currentLocation;
        drawInfo.hit = ship.isHit();

        drawInfo.bombNumber = ship.getBombNumber();
        drawInfo.temperature = ship.weapon.getTemperature();
        drawInfo.shipLife = ship.getLifeNumber();
        drawInfo.coinNumber = ship.getCoinNum();
        drawInfo.points = ship.getPoints();

        drawInfo.chickenLocations = enemies.getLocations();
        drawInfo.chickenImageNumber = enemies.getImageNumbers();

        drawInfo.coinLocations = coins.getLocations();
        drawInfo.coinImageNumber = coins.getImageNumbers();

        drawInfo.bulletLocations = ship.weapon.bullets.getLocations();
        drawInfo.bulletType = ship.weapon.bullets.getType();

        drawInfo.eggLocations = eggs.getLocations();

        drawInfo.missileLocations = ship.missilery.getMissiles().getLocations();

        drawInfo.powerUpLocations = powerUps.getLocations();
        drawInfo.powerUpType = powerUps.getType();

        drawInfo.waveNum = this.waveNum;

        drawInfo.changing = this.changingWaves;


//        if(mouseStatus.isPaused()){
//
//            GameStatus gameStatus = JSON.read();
//
//            System.out.println("wtf??");
//
//            ArrayList<Player> players = new ArrayList<>();
//            for (Player player: gameStatus.getPlayers()) {
//                if(player.getName().equals(name)){
//
//                    Player p = new Player(this.name);
//                    p.setLastGame(this);
//                    players.add(p);
//
//                }
//
//                else players.add(player);
//            }
//
//            JSON.write(new GameStatus(gameStatus.scores, players));
//
//            mouseStatus.setPaused(false);
//
//            System.exit(0);
//
//        }
    }

    public void move(int Time){

        ship.doConstantly(Time,mouseStatus,enemies,eggs,coins,powerUps);
        enemies.move(Time);
        eggs.move(Time);
        coins.move(Time);
        powerUps.move(Time);
        playingTime += Time / 1000.0;

        waveHandler();

//        if(!boss.isAlive()) boss = null;

    }

    private void waveHandler() {

//        mission.update();

        if(waveNum > 20 || ship.isDead()){

            Score s = new Score(name, ship.getPoints(),waveNum - 1, (int) playingTime);
            Player p = null;

//            GameStatus gs = JSON.read();
//
//            for (Player player: gs.getPlayers()){
//
//                if(player.getName().equals(name)){
//                    p = player;
//                    player.setLastGame(null);
//                }
//
//            }
//
//            JSON.write(gs);

            ArrayList<PlayerDB> playerDBS = PlayerDataBase.getPlayers();
            for(PlayerDB playerDB: playerDBS){

                if(playerDB.getPd().getName().substring(0,name.length()).equals(name)){

                    p = PlayerDB.createPlayer(playerDB);
                    PlayerDataBase.update(new PlayerDB(name));

                }

            }

            view.remove();
            new ScoreList(view.frame,p,s);

        }

        if(currentWave != null) {
            currentWave.toDoConstantly();
        }

//        normalWaveHandler();
        reflectionWaveHandler();
    }

    private void normalWaveHandler() {
        if(enemies.enemies.size() == 0) {
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
            if(changingWaves && System.currentTimeMillis() > changeTime + 3 * 1000) {

                if(waveNum % 5 == 1)
                    currentWave = new RectangleGroup(1,level,40, coins, powerUps, eggs);
                if(waveNum % 5 == 2)
                    currentWave = new CircleGroup(1, level, 20 , coins, powerUps, eggs);
                if(waveNum % 5 == 3)
                    currentWave = new RotatingGroup(1,level,20, coins, powerUps, eggs);
                if(waveNum % 5 == 4)
                    currentWave = new AggressiveGroup(1,level,20, coins, powerUps, eggs);

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
    private void reflectionWaveHandler() {

        if (enemies.enemies.size() == 0) {
            if (waveNum % 5 == 0 && !changingWaves) {

                level++;
                if(level != 1) levelFinished();

            }
            if (!changingWaves) {
                if(waveNum % 5 != 0) playSound("Sounds/WaveComplete.wav");
                level = waveNum / 5 + 1;
                waveNum++;
                changeTime = System.currentTimeMillis();
                changingWaves = true;
            }
            if (changingWaves && System.currentTimeMillis() > changeTime + 3 * 1000) {



                if (waveNum % 5 > 0) {

                    int index = /*new Random().nextInt(chickensClasses.size())*/ chickensClasses.size() - 1;
                    Class chickens = chickensClasses.get(index);
                    Constructor constructor = null;
                    try {
                        constructor = chickens.getConstructor(Integer.class,Integer.class,Integer.class, Coins.class, PowerUps.class, Eggs.class);
                        currentWave = (ChickenGroup) (constructor.newInstance(1,level,new Random().nextInt(10) + 10,coins,powerUps, eggs));
                        if(currentWave instanceof RotatingGroup) playSound("Sounds/Alert.wav");
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    for (Enemy enemy : currentWave.chickens) {

                        enemies.add(enemy);

                    }

                }

                if (waveNum % 5 == 0) {

                    int index = /*new Random().nextInt(bossClasses.size())*/ bossClasses.size() - 1;
                    Class boss = bossClasses.get(index);
                    Constructor constructor;
                    try {
                        constructor = boss.getConstructor(Integer.class, Eggs.class, PowerUps.class, Coins.class);
                        enemies.add((Boss) (constructor.newInstance((level),eggs,powerUps, coins)));
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }


                changingWaves = false;
            }
        }

    }


    private void levelFinished() {
        ship.levelEnd();
        if(level != 4)
            playSound("Sounds/LevelComplete.wav");
        if(level == 4) playSound("Sounds/GameComplete.wav");
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


    public static boolean isOutOfScreen(MyPoint Location){

        return ((Location.x > FullScreen.width + 10) || (Location.x < -10) || (Location.y < - 10) || (Location.y > FullScreen.height + 10));

    }

    public static MyPoint velocityByStartAndEndPoint(MyPoint endPoint , MyPoint startPoint , double pace){


        double deltaX = endPoint.x - startPoint.x  + 0.0;
        double deltaY = endPoint.y - startPoint.y  + 0.0;

        double distance = Math.sqrt((endPoint.x - startPoint.x) * (endPoint.x - startPoint.x) + (endPoint.y - startPoint.y) * (endPoint.y - startPoint.y));

        MyPoint Velocity = new MyPoint(0.0 , 0.0);

        Velocity.x =  pace * deltaX / distance;
        Velocity.y =  pace * deltaY / distance;

        return Velocity;

    }

    public static MyPoint velocityByStartAndEnd(MyPoint endPoint, MyPoint startPoint,long time){

        double distance = Math.sqrt((endPoint.x - startPoint.x) * (endPoint.x - startPoint.x) + (endPoint.y - startPoint.y) * (endPoint.y - startPoint.y));
        double pace = distance / time ;
        return velocityByStartAndEndPoint(endPoint,startPoint,pace);

    }

    public static boolean intersect(MyPoint location1 , Dimension size1 , MyPoint location2 , Dimension size2){

        MyPoint center1 = new MyPoint(location1.x + size1.width/2.0 , location1.y + size1.height/2.0);

        MyPoint center2 = new MyPoint(location2.x + size2.width/2.0 , location2.y + size2.height/2.0);

        MyPoint distance = new MyPoint(Math.abs(center1.x - center2.x),Math.abs(center1.y - center2.y));

        boolean horizontal = (distance.x < (size1.width + size2.width) / 2.0);

        boolean vertical = (distance.y < (size1.height + size2.height) / 2.0);

//        return horizontal && vertical;

//        return horizontal && (distance.y < 10);

        double d = Math.sqrt(distance.x * distance.x + distance.y * distance.y);

        double Distance = Math.min((size1.width + size2.width) / 2.0, (size1.height + size2.height) / 2.0);

        return d < Distance;

    }

    public Ship getShip() {
        return ship;
    }

    public double getPlayingTime() {
        return playingTime;
    }

    public int getWaveNum() {
        return waveNum;
    }

    public void addBoss(String directory, String className) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {

        ExtensionLoader<Boss> bossExtensionLoader = new ExtensionLoader<Boss>();

        Class newBoss = bossExtensionLoader.load(directory, className, Boss.class);

        bossClasses.add(newBoss);

    }

    public void addChickens (String directory, String className) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {

        ExtensionLoader<ChickenGroup> chickens = new ExtensionLoader<ChickenGroup>();

        Class newChickens = chickens.load(directory, className, ChickenGroup.class);

        chickensClasses.add(newChickens);
    }

    public void setPlayingTime(double playingTime) {

        this.playingTime = playingTime;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setWaveNum(int waveNum) {
        this.waveNum = waveNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SinglePlayerDrawInfo getDrawInfo() {
        return drawInfo;
    }

    public void setDrawInfoColor(String color) {
        this.drawInfo.color = color;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setMouseStatus(MouseStatus mouseStatus) {
        this.mouseStatus = mouseStatus;
    }

    public void setChangingWaves(boolean changingWaves) {
        this.changingWaves = changingWaves;
    }

    public String getDrawInfoColor() {

        return drawInfo.color;
    }
}
