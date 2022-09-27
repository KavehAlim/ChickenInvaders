package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BulletType {

    private String type;
    private String imageAddress;
    private int power;
    private int heatRate;
    private long reloadTime;


    BulletType(String type){

        this.type = type;
        imageAddress = "Images/Bullet/" + type + "Bullet.png";

        if(this.type.equals("Red")){

            imageAddress = "Images/Bullet/RedBullet.png";
            power = 1;
            heatRate = 5;
            reloadTime = 200;

        }

        if(this.type.equals("Orange")){

            power = 2;
            heatRate = 10;
            reloadTime = 400;

        }

        if(this.type.equals("Yellow")){

            power = 3;
            heatRate = 15;
            reloadTime = 600;

        }

    }

    public String getType() {
        return type;
    }

    public long getReloadTime() {
        return reloadTime;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public int getPower() {
        return power;
    }

    public int getHeatRate() {
        return heatRate;
    }

    public static BufferedImage getImage(String type){

        BufferedImage image;

        try {
            image = ImageIO.read(new File("Images/Bullet/" + type + "Bullet.png"));
        }catch ( Exception e) {
            e.printStackTrace();
            return null;
        }

        return image;

    }
}
