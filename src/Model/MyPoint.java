package Model;

public class MyPoint {

    public double x , y;

    public MyPoint(double x, double y){

        this.x = x;
        this.y = y;

    }

    public static MyPoint sum(MyPoint a , MyPoint b){

        MyPoint sum = new MyPoint(a.x + b.x , a.y + b.y);
        return sum;

    }
}
