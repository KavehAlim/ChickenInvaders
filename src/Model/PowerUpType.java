package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class PowerUpType {

    private final static String powerUps[] = {"BulletPower" , "Orange" , "Temperature" , "Yellow" , "Red"};
    private String type;
    private String imageAddress;

    PowerUpType() {

        Random random = new Random();
        int powerUpNumber = random.nextInt(5);

        type = powerUps[powerUpNumber];
        imageAddress = "Images/PowerUp/"+ type + "PowerUp.png";

    }

    String getType() {
        return type;
    }

    String getImageAddress() {
        return imageAddress;
    }

    public static BufferedImage getImage(String type){

        BufferedImage image;

        try {
            image = ImageIO.read(new File("Images/PowerUp/"+ type + "PowerUp.png"));
        }catch ( Exception e) {
            e.printStackTrace();
            return null;
        }

        return image;

    }
}
