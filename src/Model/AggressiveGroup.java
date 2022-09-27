package Model;

import Controller.Game;

import java.util.ArrayList;
import java.util.Random;

public class AggressiveGroup extends ChickenGroup {

    private static final long resetTime = 10 * 1000;
    private long lastResetTime;
    private static final double Pace = 0.1;
    private ArrayList<MyPoint> destiny;
    private int shipHitter;

    public AggressiveGroup(Integer playerNum, Integer level, Integer number, Coins coins,PowerUps powerUps, Eggs eggs) {
        super(playerNum, level, number, coins, powerUps, eggs);
        destiny = new ArrayList<>();
        setChickens();
    }

    public void setChickens() {
        super.setChickens();
        setDestiny();

        for (int i = 0; i < number ; i++) {

            MyPoint location = new MyPoint(destiny.get(i).x, destiny.get(i).y - 1000);
            Chicken chicken = new Chicken(location, new Random().nextInt(level), eggs, powerUps, coins);
            chicken.life *= Math.sqrt(playerNum);
            chickens.add(chicken);

        }

        setCenter();
    }

    private void setDestiny(){

        lastResetTime = System.currentTimeMillis();

        destiny.clear();
        Random r = new Random();
        int n = chickens.size();
        if (n == 0) n = number;
        for (int i = 0; i < n; i++) {

            MyPoint location = new MyPoint(r.nextInt(Game.FullScreen.width - 200) + 150, r.nextInt(Game.FullScreen.height) * 0.6 + 100);
            destiny.add(location);

        }

        shipHitter = r.nextInt(n);

        destiny.get(shipHitter).x = r.nextInt(420) + 630;
        destiny.get(shipHitter).y = r.nextInt(150) + 800;


    }

    @Override
    public void setVelocity(){


        for (Chicken chicken: chickens){

            int index = chickens.indexOf(chicken);

            chicken.setVelocity(Game.velocityByStartAndEndPoint(destiny.get(index), chicken.location, Pace));

            if(index == shipHitter)
                chicken.setVelocity(Game.velocityByStartAndEndPoint(destiny.get(index), chicken.location, Pace*3));

            if(Game.intersect(chicken.location, Chicken.SIZE, destiny.get(chickens.indexOf(chicken)), Chicken.SIZE))
                chicken.setVelocity(new MyPoint(0.0 , 0.0));

        }

        if(System.currentTimeMillis() > lastResetTime + resetTime){

            setDestiny();

        }


    }

    private void setCenter(){

            lastResetTime = System.currentTimeMillis();

            for (Chicken chicken: chickens){

                chicken.velocity = Game.velocityByStartAndEnd(chicken.location,Boss.makeRandomPoint(Chicken.SIZE),resetTime * 2) ;

            }

    }
}
