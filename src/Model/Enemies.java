package Model;
import java.util.ArrayList;

public class Enemies {



    public ArrayList<Enemy> enemies;

    private ArrayList<MyPoint> locations = new ArrayList<>();
    private ArrayList<Integer> imageNumbers = new ArrayList<>();


    public Enemies() {

        enemies = new ArrayList<>();

    }

    public void move(int Delay){


        for (Enemy enemy: enemies){

            enemy.move(Delay);

        }

        removeDead();

        addLocations();

    }

    private void removeDead(){

        ArrayList<Enemy> dead = new ArrayList<>();

        for (Enemy enemy: enemies){


            if(!enemy.isAlive()){

                dead.add(enemy);

            }

        }

        for (Enemy enemy: dead) {

            enemies.remove(enemy);

        }

    }

    public void add(Enemy enemy){

        enemies.add(enemy);

    }


    private void addLocations(){

        locations.clear();
        imageNumbers.clear();

        for (Enemy enemy: enemies) {

            locations.add(enemy.getLocation());
            imageNumbers.add(enemy.getCurrentImageNumber());

        }

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }

    public ArrayList<Integer> getImageNumbers() {
        return imageNumbers;
    }
}
