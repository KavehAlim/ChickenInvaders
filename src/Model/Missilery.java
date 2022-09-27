package Model;

import static View.Sound.playSound;

public class Missilery {

    private int missileNum = 3;
    private long lastMissileTime;
    private static final long ReloadTime = 300;
    public Missiles missiles = new Missiles();

//    transient private Ship ship;

    Missilery(/*Ship ship*/){

//        this.ship = ship;
        lastMissileTime = System.currentTimeMillis();

    }
    void shootMissile(MyPoint ship){

        if (canShoot()) {

                playSound("Sounds/MissileLaunch.wav");
                MyPoint location = new MyPoint(ship.x  + Ship.SIZE.width / 2.0 , ship.y + Ship.SIZE.height / 2.0);
                Missile newMissile = new Missile(location);
                missiles.add(newMissile);
                missileNum --;
                lastMissileTime = System.currentTimeMillis();
        }

    }

    private boolean canShoot() {

        return ( System.currentTimeMillis() - lastMissileTime >= ReloadTime && missileNum > 0);

    }

    public int getMissileNum() {
        return missileNum;
    }

    public void setMissileNum(int missileNum) {
        this.missileNum = missileNum;
    }

    public Missiles getMissiles() {
        return missiles;
    }
}
