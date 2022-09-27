package Model;

import Controller.Game;
import java.util.Random;

public class CircleGroup extends ChickenGroup {

    private static final double Pace = 0.1;
    private MyPoint center = new MyPoint( 800.0 , -400.0);
    private double omega = -100;
    private double radius;
    private MyPoint destiny;

    public CircleGroup(Integer playerNum, Integer level, Integer number, Coins coins,PowerUps powerUps, Eggs eggs) {
        super(playerNum, level, number, coins, powerUps, eggs);

        setChickens();
    }

    @Override
    public void setChickens() {
        super.setChickens();

        double angle;
        radius = number * Chicken.SIZE.width / (2 * Math.PI);

        setDestiny();

        for (int i = 0; i < number ; i++) {

            angle = Math.PI * 2 * i / number;


            Chicken chicken = new Chicken(center, angle, omega , radius, new Random().nextInt(level), eggs, powerUps, coins);
            chicken.life *= Math.sqrt(playerNum);
            chickens.add(chicken);

        }

        setCenterVelocity();

    }



    @Override
    public void setVelocity(){

            velocity.x = Game.velocityByStartAndEndPoint(destiny,center, Pace).x;
            velocity.y = Game.velocityByStartAndEndPoint(destiny,center, Pace).y;

            center.x += velocity.x * 5;
            center.y += velocity.y * 5;

            if(Game.intersect(center, Chicken.SIZE, destiny, Chicken.SIZE)){

                setDestiny();

            }

            setCenterVelocity();

    }

    private void setDestiny(){


        Random r = new Random();
        destiny = new MyPoint(r.nextInt((int) (Game.FullScreen.width - 2 * radius)) + radius, r.nextInt((int) (Game.FullScreen.height - 2 * radius)) * 0.8 + radius);

    }

    private void setCenterVelocity() {


        for (Chicken chicken:chickens) {

            chicken.setVelocity(velocity);

        }
    }

}
