package Model;

import Controller.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Chicken extends Enemy{

//    public static BufferedImage[] chickenImages = new BufferedImage[14];
//    private BufferedImage currentImage;

    public final static Dimension SIZE = new Dimension(75 , 75);

    private double angle = 0;
    private double omega = 0;
    private double radius = 0;

    private boolean layEgg;
    private ChickenType chickenType;

    private boolean containPowerUp;
    private static final double containPowerUpProb = 6.0;

    private boolean containCoin;
    private static final double containCoinProb = 6.0;

    private MyPoint centerLocation;

    private static final long layTime = 1000;
    private long lastLayTime;

//    {
//        setImages();
//    }

    Chicken(MyPoint Location, int level , Eggs eggs, PowerUps powerUps, Coins coins){
        super(level , eggs, powerUps, coins);

        this.level = level;
        this.Size = SIZE;
        currentImageNumber = new Random().nextInt(140);

        this.location = new MyPoint(Location.x, Location.y);
        this.centerLocation = new MyPoint(location.x , location.y);

        chickenType = new ChickenType(level);
        this.life = chickenType.getLife();

        this.setContainCoin();
        this.setContainPowerUp();
        lastLayTime = System.currentTimeMillis();

    }

    Chicken(MyPoint CenterPoint , double angle ,double rotateTime , double radius , int level , Eggs eggs,PowerUps powerUps, Coins coins){

        this(CenterPoint , level , eggs, powerUps, coins);

        this.angle = angle;
        this.omega = 2 * Math.PI/(rotateTime * 1.0 / 0.005);
        this.radius = radius;

        location.x = centerLocation.x + this.radius * Math.cos(angle);
        location.y = centerLocation.y + this.radius * Math.sin(angle);

    }

    @Override
    void move(int Time) {

        angle += Time * omega;

        super.move(Time);

        centerLocation.x += Time * velocity.x;
        centerLocation.y += Time * velocity.y;

        location.x = centerLocation.x + this.radius * Math.cos(angle);
        location.y = centerLocation.y + this.radius * Math.sin(angle);


        layEgg();

        setCurrentImage();

    }

    private void setCurrentImage(){

//        currentImage = chickenImages[currentImageNumber / 10 % 14 ];
        currentImageNumber++;

    }

    private void setLayEgg() {

        if(System.currentTimeMillis() > lastLayTime + layTime) {

            lastLayTime = System.currentTimeMillis();
            int p = new Random().nextInt(10000 * 100);
            layEgg = p % 10000 < chickenType.getLayEggProb();

        }

    }

    private void setContainCoin() {

        int p = new Random().nextInt(10000 * 100);

        containCoin = p % 100 < containCoinProb;

    }

    private void setContainPowerUp() {

        int p = new Random().nextInt(10000 * 100);

        containPowerUp = p % 100 < containPowerUpProb;

    }

//    void draw(Graphics g){
//
//            g.drawImage(currentImage, (int) location.x, (int) location.y , SIZE.width , SIZE.height , null);
//
//    }

//    public static void setImages(){
//
//        for (int i = 0; i < chickenImages.length / 2; i++) {
//
//            try {
//                chickenImages[i] = ImageIO.read(new File("Images/Chicken/Chicken" + (i+1) + ".png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        for (int i = chickenImages.length / 2; i < chickenImages.length; i++) {
//
//            try {
//                chickenImages[i] = ImageIO.read(new File("Images/Chicken/Chicken" + (chickenImages.length - i) + ".png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    void setVelocity(MyPoint velocity) {
        this.velocity = new MyPoint(velocity.x , velocity.y);
    }

    void die(){

        super.die();

        if(containCoin) {

            MyPoint location = new MyPoint(this.location.x + Chicken.SIZE.width/2 - Coin.SIZE.width/2,this.location.y + Chicken.SIZE.height/2 -Coin.SIZE.height/2);
            coins.add(new Coin(location));

        }

        if(containPowerUp) {

            MyPoint location = new MyPoint(this.location.x + Chicken.SIZE.width/2 - PowerUp.SIZE.width/2,this.location.y + Chicken.SIZE.height/2 - PowerUp.SIZE.height/2);
            powerUps.add(new PowerUp(location));

        }

    }

    private void layEgg(){

        this.setLayEgg();
        if(layEgg){

            MyPoint location = new MyPoint(this.location.x + Chicken.SIZE.width/2 - Egg.SIZE.width/2,this.location.y + Chicken.SIZE.height/2 - Egg.SIZE.height/2);
            eggs.add(new Egg(location , (this.level + 2) / 2));
            layEgg = false;

        }

    }

    int getCurrentImageNumber() {
        return currentImageNumber;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
