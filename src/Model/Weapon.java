package Model;

import static View.Sound.playSound;

public class Weapon {

    /*transient private Ship ship;*/
    private final static double overHeatChillRate = 15;
    private final static double chillRate = 20;
    private double temperature;
    private static double initialTemperature;
    private int maxTemperature;
    private long lastBulletTime;
    private boolean overheat;
    private int numOfBullet;
    private double powerOfBullet;
    private BulletType currentBulletType;
    public Bullets bullets;


    {
        initialTemperature = 0;
    }


    public Weapon(/*Ship ship*/){

        /*this.ship = ship;*/
        maxTemperature = 100;
        numOfBullet = 1;
        bullets = new Bullets();
        currentBulletType = new BulletType("Yellow");
        temperature = initialTemperature;
        powerOfBullet = 1;

    }

    public double getTemperature() {
        return temperature;
    }

    void setLastBulletTime(long lastBulletTime) {
        this.lastBulletTime = lastBulletTime;
    }

    void shootBullet(MyPoint ship){

        if (canShootBullet()) {

            playSound("Sounds/ShootSound.wav");

            temperature += currentBulletType.getHeatRate();

            for (int i = 0; i < numOfBullet; i++) {

                MyPoint location = new MyPoint(ship/*.currentLocation*/.x   , ship/*.currentLocation*/.y);
                location.x += (Ship.SIZE.width + 0.0) / (numOfBullet + 1) * (i + 1);
                location.x -= Bullet.SIZE.width / 2;
//                Bullet newBullet = new Bullet(location, this, currentBulletType.getType());
                Bullet newBullet = new Bullet(location, this, currentBulletType.getType(), Math.PI /2 - i * Math.PI / 40.0 + (numOfBullet - 1) * Math.PI / 80.0);
                newBullet.setPower(newBullet.getPower() * powerOfBullet);
                bullets.add(newBullet);

            }
            if (temperature >= maxTemperature) {

                overheat = true;

//                System.out.println("overheat");

            }


        }


    }

    private boolean canShootBullet() {

        return ( System.currentTimeMillis() - lastBulletTime >= currentBulletType.getReloadTime()) && !overheat;

    }

    void chill(long Time) {

            if (overheat) {

                temperature = Math.max(initialTemperature, temperature - (overHeatChillRate / 1000) * Time);
                if (temperature <= 0) {

                    overheat = false;

                }

            } else {

                temperature = Math.max(initialTemperature, temperature - (chillRate / 1000) * Time);

            }

    }

    void getPowerUp(PowerUp powerUp){

        String type = powerUp.powerUpType.getType();

        if(type.equals( "BulletPower")){

            bulletPowerPowerUp();
            return;

        }

        if (type.equals("Temperature")){

            this.maxTemperature +=5;
            return;


        }

        if(currentBulletType.getType().equals(type)){
            bulletPowerPowerUp();
        }

        else currentBulletType = new BulletType(type);
    }

    private void bulletPowerPowerUp() {
        if(numOfBullet>3) powerOfBullet += 0.25;
        else  numOfBullet++;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getNumOfBullet() {
        return numOfBullet;
    }

    public double getPowerOfBullet() {
        return powerOfBullet;
    }

    public BulletType getCurrentBulletType() {
        return currentBulletType;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setNumOfBullet(int numOfBullet) {
        this.numOfBullet = numOfBullet;
    }

    public void setPowerOfBullet(double powerOfBullet) {
        this.powerOfBullet = powerOfBullet;
    }

    public void setCurrentBulletType(String type) {
        this.currentBulletType = new BulletType(type);
    }
}
