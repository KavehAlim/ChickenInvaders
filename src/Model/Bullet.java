package Model;

import Controller.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet {

    private boolean alive;

    BulletType bulletType;
    private double power;
//    private String imageAddress;
//    public BufferedImage image;
    MyPoint location;

    public final static Dimension SIZE = new Dimension(20 ,33);

    private final static MyPoint VELOCITY = new MyPoint(0 , -1);

    private final static double pace = 1;

    private MyPoint velocity;


    Bullet(MyPoint location , Weapon weapon , String type){

        this.setBulletType(type);
        alive = true;
        weapon.setLastBulletTime(System.currentTimeMillis());
        this.location = new MyPoint(location.x , location.y);
        velocity = new MyPoint(VELOCITY.x, VELOCITY.y);

    }

    Bullet(MyPoint location , Weapon weapon , String type, double alpha){

        this(location, weapon, type);
        velocity.x =  pace * Math.cos(alpha);
        velocity.y = (-1) * pace * Math.sin(alpha);

    }

//    public void draw(Graphics g) {
//
//        g.drawImage(this.image, (int)this.location.x, (int)this.location.y ,null);
//
//    }

    public void move(int Time) {

        location.x = location.x + (Time * velocity.x);
        location.y = location.y + (Time * velocity.y);

    }

    void setBulletType(String type){

        bulletType = new BulletType(type);
        power = bulletType.getPower();
//        imageAddress = bulletType.getImageAddress();
//        setImage();

    }

//    void setImage() {
//
//        try {
//            image = ImageIO.read(new File(imageAddress));
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void die(){

        alive = false;

    }

    public boolean isAlive() {
        return alive;
    }

    public MyPoint getLocation() {

        return location;

    }

    double getPower(){

        return power;

    }

    void setPower(double v) {

        power = v;

    }
}
