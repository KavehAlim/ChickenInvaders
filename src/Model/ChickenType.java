package Model;

class ChickenType {

    private int level;
    private double life;
    private double layEggProb;
    private static final double[] lives = {2.0, 3.0, 5.0, 8.0};
    private static final double[] layEggProbes = {5.0, 5.0, 10.0, 20.0};

    ChickenType(int level){

        this.level = level;
        life = lives[level];
        layEggProb = layEggProbes[level];

    }

    double getLife() {
        return life;
    }

    double getLayEggProb() {
        return layEggProb;
    }

}
