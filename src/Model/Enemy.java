package Model;

import java.awt.*;

import static View.Sound.playSound;

public abstract class Enemy {

    private boolean alive;
    double life;
    int level;
    protected Eggs eggs;
    protected PowerUps powerUps;
    protected Coins coins;
    protected MyPoint location;
    protected MyPoint velocity;
    int currentImageNumber;
    boolean boss;
    Dimension Size;

    Enemy(int level, Eggs eggs, PowerUps powerUps, Coins coins){

        Size = new Dimension();
        this.alive = true;
        this.level = level;
        this.eggs = eggs;
        this.coins = coins;
        this.powerUps = powerUps;
        this.velocity = new MyPoint(0.0 , 0.0);

    }


    void move(int Time){

        location.x = location.x + (Time * velocity.x);
        location.y = location.y + (Time * velocity.y);

    }

    void hit(double power){

        life -= power;
        if (life <= 0 && alive){

            die();

        }

    }

    void die(){

        this.alive = false;

    }


    public MyPoint getLocation() {

        return location;

    }

    public boolean isAlive() {
        return alive;
    }

    int getLevel() {
        return level;
    }

    int getCurrentImageNumber(){

        if(boss) return level * (-1);
        else return currentImageNumber;

    }

    private void setCurrentImage(){

//        currentImage = chickenImages[currentImageNumber / 10 % 14 ];
        if(!boss) currentImageNumber++;

    }

    public Dimension getSize() {
        return Size;
    }

    public double getLife() {
        return life;
    }

    public MyPoint getVelocity() {
        return velocity;
    }
}
