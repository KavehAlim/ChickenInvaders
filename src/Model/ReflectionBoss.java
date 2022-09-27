package Model;

public class ReflectionBoss extends Boss {

    public ReflectionBoss(Integer level, Eggs eggs, PowerUps powerUps, Coins coins) {
        super(level, eggs, powerUps, coins);
        location = new MyPoint(- Boss.SIZE.width,0.0);
    }

    @Override
    public void move(int Time) {
        super.move(Time);

        resetVelocity();
    }

    @Override
    public void resetVelocity() {

            if (location.y < 10 && location.x < 1200) {

                velocity.x = 0.1;
                velocity.y = 0.0;

            }

            if (getLocation().x > 1200 && location.y < 700) {

                velocity.x = 0.0;
                velocity.y = 0.1;

            }

            if (location.y > 700 && location.x > 10) {

                velocity.x = -0.1;
                velocity.y = 0.0;

            }

            if (location.x < 10 && location.y > 10) {

                velocity.x = 0.0;
                velocity.y = - 0.1;

            }

    }

}
