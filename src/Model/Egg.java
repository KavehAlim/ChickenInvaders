package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Egg {

    private boolean alive;

    private static final MyPoint VELOCITY = new MyPoint(0.0 , 0.15);
    public final static Dimension SIZE = new Dimension(20 , 27);
//    private static final String imageAddress = "Images/Chicken/Egg.png";

    private MyPoint location;
    private MyPoint velocity;
//    public static BufferedImage image;
//
//    {
//
//        setImage();
//
//    }

    Egg(MyPoint location, int velocity){

        alive = true;
        this.location = new MyPoint(location.x , location.y);
        this.velocity = new MyPoint(velocity * VELOCITY.x,velocity * VELOCITY.y);

    }

    public void move(int Time){

        location.x = location.x + (Time * velocity.x);
        location.y = location.y + (Time * velocity.y);

    }

//    public void draw(Graphics g){
//
//        g.drawImage(image, (int)this.location.x, (int)this.location.y ,null);
//
//    }

//    public static void setImage() {
//
//        try {
//            image = ImageIO.read(new File(imageAddress));
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public MyPoint getLocation() {
        return location;
    }

    void die(){

        this.alive = false;

    }

    public boolean isAlive() {
        return alive;
    }

    public void setVelocity(MyPoint velocity) {
        this.velocity = velocity;
    }

}


