package Model;

import java.util.ArrayList;
import java.util.Random;

public class RectangleGroup extends ChickenGroup {


    private int column;
    private int row;
    private ArrayList <MyPoint> destiny;
    private ArrayList <Boolean> arrived;

    MyPoint groupVelocity = new MyPoint(0.0 , 0.1);

    private boolean reset;
    private boolean reShape;
    private static final long reShapeTime = 3 * 1000;
    private long lastReShape;


    public RectangleGroup(Integer playerNum, Integer level, Integer number, Coins coins,PowerUps powerUps, Eggs eggs) {
        super(playerNum, level, number, coins, powerUps, eggs);

        destiny = new ArrayList<>();
        arrived = new ArrayList<>();
        reset = false;

        setChickens();
    }

    @Override
    public void setChickens() {
        super.setChickens();

        row = 5;
        column = number/row;

        for (int i = 0; i < column; i++) {

            for (int j = 0; j < row; j++) {

                MyPoint ChickenLocation = new MyPoint(startPoint.x + (endPoint.x - startPoint.x) * i / column , startPoint.y + (endPoint.y - startPoint.y) * j / row - 1240.0);
                Chicken chicken = new Chicken(ChickenLocation,Math.random(), Math.random() + 2,5, new Random().nextInt(level), eggs, powerUps, coins);
                chicken.life *= Math.sqrt(playerNum);
                this.add(chicken);
                chicken.setVelocity(groupVelocity);

            }

        }

//        some();

    }

    @Override
    public void setVelocity(){

        for (Chicken chicken:chickens) {

            if(chicken.getLocation().y > endPoint.y && groupVelocity.x == 0 /* && chicken.getVelocity().y != 0.0*//* && !reShape*/){

                reset = true;
                groupVelocity.x = 0.1;
                groupVelocity.y = 0.0;
            }

            if(chicken.getLocation().x > endPoint.x + 380){


                reset = true;
                groupVelocity.x = - 0.1;
                groupVelocity.y = 0.0;

            }

            if(chicken.getLocation().x < startPoint.x - 400){


                reset = true;
                groupVelocity.x = 0.1;
                groupVelocity.y = 0.0;

            }

//            if(arrived.indexOf(true)== -1){
//                reset = true;
//                reShape = false;
//                System.out.println(chicken.location.x + " " + chicken.location.y);
//            }
//
//            if(Math.abs(chicken.getLocation().x - destiny.get(chickens.indexOf(chicken)).x) < 10.0 && Math.abs(chicken.getLocation().y - destiny.get(chickens.indexOf(chicken)).y) < 10.0) {
//                arrived.set(chickens.indexOf(chicken), true);
//            }

        }

        if(reset){


            for (Chicken chicken:chickens) {

                chicken.setVelocity(groupVelocity);

            }

            reset = false;
        }
    }

//    @Override
//    public void reShape() {
//        super.reShape();
//
//        if((column-1)*row == chickens.size() && !reShape){
//
//
//            column--;
//
//            some();
//        }
//
//    }
//
//    private void some(){
//
//
//        reShape =true;
//        lastReShape = System.currentTimeMillis();
//        destiny.clear();
//
//        for (Chicken chicken:chickens){
//
//            int n = chickens.indexOf(chicken);
//            int j = n % row;
//            int i = n / row;
//            MyPoint ChickenLocation = new MyPoint(startPoint.x + (endPoint.x - startPoint.x) * i / column, startPoint.y + (endPoint.y - startPoint.y) * j / row);
//            chickens.get(i * row + j).goToLocation(ChickenLocation, reShapeTime);
//            destiny.add(new MyPoint(ChickenLocation.x, ChickenLocation.y));
//            arrived.add(false);
//
//        }
//
//    }
}
