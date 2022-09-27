package Model;

import java.util.Random;

public class ReflectionGroup extends ChickenGroup {

    public ReflectionGroup(Integer playerNum, Integer level, Integer number, Coins coins, PowerUps powerUps, Eggs eggs) {
        super(playerNum, level, number, coins, powerUps, eggs);
        setChickens();
    }



    @Override
    public void setChickens() {
        super.setChickens();

        for (int i = 0; i < number; i++) {

                MyPoint ChickenLocation = new MyPoint(- Chicken.SIZE.width * i ,  0.0);
                Chicken chicken = new Chicken(ChickenLocation,Math.random(), Math.random() + 2,5, new Random().nextInt(level) , eggs, powerUps, coins);
                chicken.life *= Math.sqrt(playerNum);
                this.add(chicken);

        }

    }


    @Override
    public void setVelocity() {

        for (Chicken chicken : chickens) {

            if (chicken.location.y < 10 && chicken.location.x < 1600) {

                chicken.velocity.x = 0.1;
                chicken.velocity.y = 0.0;

            }

            if (chicken.getLocation().x > 1600 && chicken.location.y < 900) {

                chicken.velocity.x = 0.0;
                chicken.velocity.y = 0.1;

            }

            if (chicken.location.y > 900 && chicken.location.x > 10) {

                chicken.velocity.x = -0.1;
                chicken.velocity.y = 0.0;

            }

            if (chicken.location.x < 10 && chicken.location.y > 10) {

                chicken.velocity.x = 0.0;
                chicken.velocity.y = - 0.1;

            }

        }

    }
}
