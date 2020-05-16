package tarantula.data;

import tarantula.matrices.Vector;

import java.util.ArrayList;

public class Pair {

    private ArrayList<Double> x;
    private ArrayList<Double> y;

    public Pair(ArrayList<Double> x, ArrayList<Double> y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Double> getY() {
        return y;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public Vector getXVector() {
        return Vector.fromArrayList(this.x);
    }

    public Vector getYVector() {
        return Vector.fromArrayList(this.y);
    }

    public void normalize(double val) {
        ArrayList<Double> newX = new ArrayList<Double>();
        ArrayList<Double> newY = new ArrayList<Double>();
        for(double i : this.x)
            newX.add(i / val);
        for(double i : this.y)
            newY.add(i / val);
        this.x = newX;
        this.y = newY;
    }

}
