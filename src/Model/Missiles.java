package Model;

import java.util.ArrayList;

public class Missiles {

    ArrayList<Missile> missiles = new ArrayList<>();
    private ArrayList <MyPoint> locations = new ArrayList<>();

//    public void draw(Graphics g){
//
//        for (Missile missile: missiles){
//
//            missile.draw(g);
//
//        }
//
//    }

    public void move(int Delay){

        for (Missile missile: missiles){

            missile.move(Delay);

        }

        removeCollapsed();

        addLocations();

    }

    private void addLocations(){

        locations.clear();

        for (Missile missile: missiles) {

            locations.add(missile.getLocation());

        }

    }

    private void removeCollapsed(){

        ArrayList<Missile> collapsed = new ArrayList<>();

        for (Missile missile: missiles){

            if(!missile.alive)  {

                collapsed.add(missile);

            }

        }

        for (Missile missile: collapsed) {

            if(!missile.alive) {

                missiles.remove(missile);

            }

        }

    }

    public void add(Missile missile){

        missiles.add(missile);

    }

    public ArrayList<MyPoint> getLocations() {
        return locations;
    }
}

