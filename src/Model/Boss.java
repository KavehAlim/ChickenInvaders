package Model;

import Controller.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Boss extends Enemy{

//    public static BufferedImage[] images = new BufferedImage[4];
//    private BufferedImage image;

    private static final double Pace = 0.05;

    private BossEggShooter shooter;

    public final static Dimension SIZE = new Dimension(400,300);
    private MyPoint destiny;


    {

//        Size = new Dimension(50,50);
//        setImage();

    }


    public Boss(Integer level, Eggs eggs, PowerUps powerUps, Coins coins){

        super(level, eggs, powerUps, coins);
        boss = true;
        location = new MyPoint(800.0, -300.0);
        velocity = new MyPoint(0.0 , 0.0);
        this.life = 250 * (level);
        shooter = new BossEggShooter(this.eggs);
//        image = images[level-1];
        Size.height = SIZE.height;
        Size.width = SIZE.width;
        setDestiny();

    }

//    public void draw(Graphics g) {
//
//        g.drawImage(image, (int)this.location.x , (int)this.location.y  ,null);
//
//    }

    private void setDestiny(){

        Random r = new Random();
        destiny = new MyPoint(r.nextInt(Game.FullScreen.width - 500) + 100, r.nextInt(Game.FullScreen.height) * 0.3 + 100);

    }

    public void move(int Time) {

        super.move(Time);

        shooter.shoot(location);

//        if(System.currentTimeMillis() - lastResetTime > resetTime){
//
//            resetVelocity();
//
//        }

        resetVelocity();

    }
//    private void setImage(){
//
//        try {
//
//            for (int i = 0; i < images.length; i++) {
//
//                images[i] = ImageIO.read(new File("Images/Boss/Boss" + (i+1) +".png"));
//
//            }
//
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    void resetVelocity() {

        /*lastResetTime = System.currentTimeMillis();
        this.velocity = setVelocityForRandomMove(this.location,Size,Pace) ;*/

        velocity.x = Game.velocityByStartAndEndPoint(destiny,location, Pace).x;
        velocity.y = Game.velocityByStartAndEndPoint(destiny,location, Pace).y;

        if(Game.intersect(location, Chicken.SIZE, destiny, Chicken.SIZE)){

            setDestiny();

        }

    }

    static MyPoint setVelocityForRandomMove(MyPoint location, Dimension SIZE, double PACE){

        return Game.velocityByStartAndEndPoint(makeRandomPoint(SIZE),location,PACE);

    }

    static MyPoint makeRandomPoint(Dimension SIZE){

        double x = new Random().nextInt(Game.FullScreen.width - SIZE.width);
        double y = new Random().nextInt(Game.FullScreen.height - SIZE.height);
        MyPoint endPoint = new MyPoint(x,y);
        return endPoint;

    }

    @Override
    int getCurrentImageNumber() {
        return level * (-1);
    }

    @Override
    void die() {

        super.die();

        for (int i = 0; i < 5; i++) {

            MyPoint location = new MyPoint(this.location.x + Boss.SIZE.width/2 - PowerUp.SIZE.width/2 + new Random().nextInt(200) - 100,this.location.y + Boss.SIZE.height/2 - PowerUp.SIZE.height/2);
            powerUps.add(new PowerUp(location));

        }

    }
}
