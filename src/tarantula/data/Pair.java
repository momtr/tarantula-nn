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
        for(int i = 0; i < this.x.size() -1; i++) {
            this.x.set(i, this.x.get(i) / val);
        }
        for(int i = 0; i < this.y.size() -1; i++) {
            this.y.set(i, this.y.get(i) / val);
        }
    }

}
