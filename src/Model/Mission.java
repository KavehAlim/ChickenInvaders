package Model;

public class Mission {

    ChickenGroup wave1;
//    ChickenSystem wave2;
//    ChickenSystem wave3;
//    ChickenSystem wave4;
    Boss boss;
    Enemies enemies;
    int waveNum;
    long time;
    Eggs eggs;
    Coins coins;
    PowerUps powerUps;
    ChickenGroup currentWave;
    int level;

    boolean changingWaves;

    public Mission(int level, Enemies enemies,Eggs eggs, PowerUps powerUps, Coins coins){


        changingWaves = false;
        time = System.currentTimeMillis();
        this.level = level;
        waveNum = 0;
        this.enemies = enemies;
        this.powerUps = powerUps;
        this.coins = coins;
        this.eggs = eggs;

    }

    public void update(){

        if(currentWave != null) {
            currentWave.toDoConstantly();
        }

        if(enemies.enemies.size() == 0) {
            if(!changingWaves) {
                waveNum++;
                time = System.currentTimeMillis();
                changingWaves = true;
            }
            if(changingWaves && System.currentTimeMillis() > time + 5 * 100) {

                if(waveNum == 1)
                    currentWave = new RectangleGroup(1,level,40, coins, powerUps, eggs);
                if(waveNum == 2)
                    currentWave = new CircleGroup(1, level, 20 , coins, powerUps, eggs);
                if(waveNum == 3)
                    currentWave = new RotatingGroup(1,level,20, coins, powerUps, eggs);
                if(waveNum == 4)
                    currentWave = new AggressiveGroup(1,level,20, coins, powerUps, eggs);

                if(waveNum < 5) {
                    for (Enemy enemy : currentWave.chickens) {

                        enemies.add(enemy);

                    }
                }

                if(waveNum == 5){
                    boss = new Boss(level, eggs,powerUps, coins);
                    enemies.add(boss);
                }

//                if(waveNum > 5)
//                    System.out.println("mission completed");

                changingWaves =false;
            }
        }
    }
}
