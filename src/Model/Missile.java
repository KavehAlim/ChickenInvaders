package Model;

import Controller.Game;

import java.awt.*;

public class Missile {

    private MyPoint location;

    private final static Dimension FullScreen = new Dimension(1680, 975);
    private final static MyPoint LastLocation = new MyPoint(FullScreen.width/2 , FullScreen.height/2);
    public final static Dimension SIZE = new Dimension(30 , 20);

    private MyPoint Velocity = new MyPoint(0.0 , 0.0);

    private final static Double Pace = 2.0 / 10;

//    public static BufferedImage image;

    boolean alive;

//    {
//        setImage();
//    }

    Missile(MyPoint Location){

        this.location = new MyPoint(Location.x , Location.y);
        alive = true;
        setVelocity();

    }

    boolean shouldCollapse(){

        if ((location.x > LastLocation.x - FullScreen.width / 20) && (location.y > LastLocation.y - FullScreen.height / 20) && (location.x < LastLocation.x + FullScreen.width / 20) && (location.y < LastLocation.y + FullScreen.height / 20)){

            alive = false;
            return true;

        }

        return false;

    }

    private void setVelocity(){

        MyPoint newVelocity = Game.velocityByStartAndEndPoint(LastLocation , location, Pace);
        Velocity.x = newVelocity.x;
        Velocity.y = newVelocity.y;

    }


//    void draw(Graphics g){
//
//        g.drawImage(image, (int) location.x , (int) location.y ,null);
//
//    }

    void move(int Time) {

        location.x += (Time * Velocity.x);
        location.y += (Time * Velocity.y);

        setVelocity();

    }

//    public static void setImage() {
//
//        try {
//            image = ImageIO.read(new File("Images/StatusBar/MissileIcon.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public MyPoint getLocation() {
        return location;
    }
}
