package data;

import java.util.ArrayList;

public class Dataset {

    private ArrayList<Pair> data;

    public Dataset() {
        data = new ArrayList<Pair>();
    }

    public void addPair(Pair p) {
        this.data.add(p);
    }

    public void addExample(double[] x, double[] y) {
        ArrayList<Double> xList = new ArrayList<Double>();
        ArrayList<Double> yList = new ArrayList<Double>();
        for(int i = 0; i < x.length; i++) {
            xList.add(x[i]);
        }
        for(int i = 0; i < y.length; i++) {
            yList.add(y[i]);
        }
        this.data.add(new Pair(xList, yList));
    }

    public ArrayList<Pair> getPairs() {
        return data;
    }
}
