package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PowerUp {

    PowerUpType powerUpType;
    private boolean alive;
//    BufferedImage image;
    private MyPoint location;
    public final static Dimension SIZE = new Dimension(30 ,30);
    private final static MyPoint VELOCITY = new MyPoint(0 , 0.1);


    PowerUp(MyPoint location){

        this.setPowerUpType();
        alive = true;
        this.location = new MyPoint(location.x , location.y);

    }

//    public void draw(Graphics g) {
//
//        g.drawImage(this.image, (int)this.location.x, (int)this.location.y ,null);
//
//    }

    public void move(int Time) {

        location.x = location.x + (Time * VELOCITY.x);
        location.y = location.y + (Time * VELOCITY.y);

    }


    void setPowerUpType(){

        powerUpType = new PowerUpType();
//        setImage();

    }

//    void setImage() {
//
//        try {
//            image = ImageIO.read(new File(powerUpType.getImageAddress()));
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public MyPoint getLocation() {
        return location;
    }

    void die(){

        alive = false;

    }

    public boolean isAlive() {
        return alive;
    }
}
