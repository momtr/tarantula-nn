package data;

import matrix.Vector;

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

}
