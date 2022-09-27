package Model;

import java.util.Random;

public class RotatingGroup extends ChickenGroup {

    private MyPoint center = new MyPoint( 800.0 , 400.0);
    private double omega = -20;
    private double radius;

    public RotatingGroup(Integer playerNum, Integer level, Integer number, Coins coins,PowerUps powerUps, Eggs eggs) {
        super(playerNum, level, number, coins, powerUps, eggs);

        setChickens();
    }

    @Override
    public void setChickens() {
        super.setChickens();

        double angle;
        radius = number * Chicken.SIZE.width / (2 * Math.PI) * 15;

        for (int i = 0; i < number ; i++) {

            angle = Math.PI * 2 * i / number;


            Chicken chicken = new Chicken(center, angle, omega , radius, new Random().nextInt(level), eggs, powerUps, coins);
            chicken.life *= Math.sqrt(playerNum);
            chickens.add(chicken);

        }

        setVelocity();
    }


    @Override
    public void setVelocity(){

            setRadius();

    }

    private void setRadius(){

        if(radius > number * Chicken.SIZE.width / (2 * Math.PI) * 1.5){

            radius *= 0.995;

            for (Chicken chicken: chickens){

                chicken.setRadius(radius);

            }

        }


    }
}
