package Model;

import java.util.Random;

class BossEggShooter {

    private long lastShootTime;
    private static final long ReloadTime = 1000;

    private Eggs eggs;

    private static final double PACE = 0.15;

    BossEggShooter(Eggs eggs){

        this.eggs = eggs;
        lastShootTime = System.currentTimeMillis();

    }
    void shoot(MyPoint loc){

        if(canShoot()){

            for (int i = 0; i < 8; i++) {

                MyPoint location = new MyPoint(loc.x + Boss.SIZE.width / 2.0 - Egg.SIZE.width /2.0, loc.y + Boss.SIZE.height / 2.0 - Egg.SIZE.height /2.0);

                if(new Random().nextInt(4) % 4 == 0){



                    Egg newEgg = new Egg(location , 1);

                    double v = Math.PI / 4.0 * i;
                    MyPoint velocity = new MyPoint(PACE * Math.cos(v) , PACE * Math.sin(v));

                    newEgg.setVelocity(velocity);

                    eggs.add(newEgg);

                }

            }

            lastShootTime = System.currentTimeMillis();

        }

    }

    private boolean canShoot() {

        return ( System.currentTimeMillis() - lastShootTime >= ReloadTime);

    }

}
