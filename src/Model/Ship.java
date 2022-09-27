package Model;

import Controller.Game;
import Controller.MouseStatus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static View.Sound.playSound;

public class Ship {

    public Weapon weapon;
    public Missilery missilery;

    private static final MyPoint InitialLocation = new MyPoint(800.0 ,900.0);
    public final static Dimension SIZE = new Dimension(150 ,120);

    public MyPoint currentLocation;

//    public static BufferedImage ShipImage;

    private boolean dead;
    private int lifeNumber;

    private boolean hit;
    private long hitTime;
    private final static long rebornTime = 5 * 1000;

    private int points;
    private int coinNum;

    public Ship(){

        weapon = new Weapon();
        missilery = new Missilery();

        coinNum = 0;
        points = 0;
        lifeNumber = 5;

        currentLocation = new MyPoint(InitialLocation.x,InitialLocation.y);
//        setShipImage();

    }

    public void doConstantly(int delay , MouseStatus mouseStatus, Enemies enemies, Eggs eggs, Coins coins, PowerUps powerUps){

        weapon.bullets.move(delay);
        missilery.getMissiles().move(delay);
        if(mouseStatus.isRightClick()) shootMissile();
        if(mouseStatus.isLeftClick()) shootBullet();
        this.setCurrentLocation(mouseStatus.getMouseLocation());
        checkStatus(enemies,eggs,coins,powerUps);
        resetHit();
        chill(delay);
    }
    private void shootBullet(){

        if(!hit) weapon.shootBullet(this.currentLocation);

    }

    private void shootMissile() {

        if(!hit) missilery.shootMissile(this.currentLocation);

    }


    private void chill(long time) {

            weapon.chill(time);

    }

    public void setCurrentLocation(MyPoint Location){

        currentLocation.x = Location.x + 0.0;
        currentLocation.y = Location.y + 0.0;

    }

    public boolean isHit() {
        return hit;
    }

    public boolean resetHit(){

        if(hit && !dead)
            if(System.currentTimeMillis() > hitTime + rebornTime)
                hit = false;

        return hit;

    }

//    public static void setShipImage() {
//
//        try {
//            ShipImage = ImageIO.read(new File("Images/SpaceShip/RedSpaceShip.png"));
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public int getLifeNumber() {
        return lifeNumber;
    }

    private void checkStatus(Enemies enemies, Eggs eggs, Coins coins, PowerUps powerUps) {

        for (Enemy enemy: enemies.enemies) {

            if (Game.intersect(this.currentLocation, SIZE, enemy.getLocation(), enemy.getSize()) && !hit) {

                enemy.hit(20);
                this.die();

            }

            for (Bullet bullet : weapon.bullets.bullets) {

                if (Game.intersect(bullet.location, Bullet.SIZE, enemy.getLocation(), enemy.Size) && !Game.isOutOfScreen(enemy.location)) {

                    enemy.hit(bullet.getPower());
                    bullet.die();

                }

            }

            for (Missile missile : missilery.getMissiles().missiles) {

                if (missile.shouldCollapse()) {

                    if(!Game.isOutOfScreen(enemy.location))enemy.hit(50);

                }

            }

            boolean playSound = false;

            if (!enemy.isAlive()){

                if(!playSound) {
                    playSound("Sounds/pukpuk.wav");
                    playSound = true;
                }
                if(enemy instanceof Boss)
                    this.points += 125 * enemy.getLevel();
                else
                    this.points += enemy.getLevel() + 1;
            }

        }


            for (Coin coin : coins.coins) {

                if (Game.intersect(this.currentLocation, SIZE, coin.getLocation(), Coin.SIZE) && !hit) {

                    coin.die();
                    coinNum++;
                    playSound("Sounds/TaDa.wav");

                }

                for (Bullet bullet : weapon.bullets.bullets) {

                    if (Game.intersect(bullet.location, Bullet.SIZE, coin.getLocation(), Coin.SIZE)) {

                        coin.die();
                        bullet.die();

                    }

                }

            }

            for (PowerUp powerUp : powerUps.powerUps) {

                if (Game.intersect(this.currentLocation, SIZE, powerUp.getLocation(), PowerUp.SIZE) && !hit) {

                    powerUp.die();
                    weapon.getPowerUp(powerUp);
                    playSound("Sounds/TaDa.wav");

                }

            }

            powerUps.removeDead();

            for (Egg egg : eggs.eggs) {

                if (Game.intersect(this.currentLocation, SIZE, egg.getLocation(), Egg.SIZE) && !hit) {

                    egg.die();
                    this.die();

                }

            }


//        if(!boss.isAlive()) {
//
//            this.points += boss.getLevel();
//            points += coinNum * 3;
//            coinNum = 0;
//            missilery.setMissileNum(missilery.getMissileNum() + 1);
//            System.out.println("level completed");
//
//        }

    }

    public void die(){

        lifeNumber--;
        coinNum =0;
        hitTime = System.currentTimeMillis();
        hit = true;
        weapon = new Weapon();

        if(lifeNumber <= 0){

            this.dead();

        }

        playSound("Sounds/ShipDie.wav");


    }

    public void levelEnd(){

        if(!dead) {

            missilery.setMissileNum(missilery.getMissileNum() + 1);
            points += coinNum;
            coinNum = 0;

        }

    }

    private void dead(){

        dead = true;

    }

    public boolean isDead() {
        return dead;
    }

    public int getPoints() {
        return points;
    }

    public int getCoinNum() {
        return coinNum;
    }

    public int getBombNumber() {

        return missilery.getMissileNum();

    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setMissileNum(int missileNum) {
        this.missilery.setMissileNum(missileNum);
    }

    public void setLifeNumber(int lifeNumber) {
        this.lifeNumber = lifeNumber;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCoinNum(int coinNum) {
        this.coinNum = coinNum;
    }

    public static BufferedImage getImage(String color){

        BufferedImage image;

        try {
            image = ImageIO.read(new File("Images/SpaceShip/" + color + "SpaceShip.png"));
        }catch ( Exception e) {
            e.printStackTrace();
            return null;
        }

        return image;
    }
}
