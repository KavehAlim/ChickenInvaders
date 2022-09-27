package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Coin {

    private boolean alive;

//    public static BufferedImage[] coinImages = new BufferedImage[16];
//    private BufferedImage currentImage;
    private int currentImageNumber;
    private MyPoint location;

    private final static MyPoint VELOCITY = new MyPoint(0 , 0.2);

    public final static Dimension SIZE = new Dimension(30 , 32);
    private static Random randomCreator = new Random();

//    {
//        setImages();
//    }

    Coin(MyPoint location){

        alive = true;
        this.location = location;
        currentImageNumber = randomCreator.nextInt(160);

    }

    void move(int Time) {

        location.x += VELOCITY.x * Time;
        location.y += VELOCITY.y * Time;

        setCurrentImage();

    }

//    public static void setImages(){
//
//        for (int i = 0; i < coinImages.length / 2; i++) {
//
//            try {
//                coinImages[i] = ImageIO.read(new File("Images/Coin/Coin" + (i+1) + ".png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        for (int i = coinImages.length / 2; i < coinImages.length; i++) {
//
//            try {
//                coinImages[i] = ImageIO.read(new File("Images/Coin/Coin" + (coinImages.length - i) + ".png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    private void setCurrentImage(){

//        currentImage = coinImages[currentImageNumber / 10 % 16 ];
        currentImageNumber++;

    }

//    void draw(Graphics g){
//
//            g.drawImage(currentImage, (int) location.x, (int) location.y , SIZE.width , SIZE.height , null);
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

    int getCurrentImageNumber() {

        return  currentImageNumber;
    }
}
