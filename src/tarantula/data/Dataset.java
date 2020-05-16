package tarantula.data;

import tarantula.matrices.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dataset {

    private ArrayList<Pair> data;
    private double maxValue;

    public Dataset() {
        data = new ArrayList<Pair>();
    }

    public void addPair(Pair p) {
        for(double i : p.getX()) {
            if(i > this.maxValue)
                this.maxValue = i;
        }
        for(double i : p.getY()) {
            if(i > this.maxValue)
                this.maxValue = i;
        }
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
        this.addPair(new Pair(xList, yList));
    }

    public void readFile(String path) {
        try {
            this.readFile(path, ",", 0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void readFile(String path, String delimiter, int yColIndex) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        while(line != null && line.length() > 0) {
            String[] split = line.split(delimiter);
            ArrayList<Double> x = new ArrayList<Double>();
            ArrayList<Double> y = new ArrayList<Double>();
            for(int i = 0; i < split.length; i++) {
                Double parsedDouble = Double.parseDouble(split[i]);
                if(i == yColIndex)
                    y.add(parsedDouble);
                else
                    x.add(parsedDouble);
            }
            this.addPair(new Pair(x, y));
            line = br.readLine();
        }
    }

    public ArrayList<Pair> getPairs() {
        return data;
    }

    public void normalizeData() {
        for(int i = 0; i < this.size(); i++) {
            this.data.get(i).normalize(this.maxValue);
        }
    }

    public Matrix normalizeMatrix(Matrix input) {
        Matrix m = input.clone();
        m.multiply(1 / this.maxValue);
        return m;
    }

    public Matrix denormalizeMatrix(Matrix input) {
        Matrix m = input.clone();
        m.multiply(this.maxValue);
        return m;
    }

    public void shuffleData() {
        Collections.shuffle(this.data);
    }

    public int size() {
        return this.data.size();
    }

    public List<Dataset> trainValidationTest(float train, float validation) {
        List<Dataset> datasets = new ArrayList<Dataset>();
        datasets.add(new Dataset()); // train
        datasets.add(new Dataset()); // validation
        datasets.add(new Dataset()); // test
        for(Pair p : data) {
            double num = Math.random();
            if(num < train)             datasets.get(0).addPair(p);
            else if(num < validation)   datasets.get(1).addPair(p);
            else                        datasets.get(2).addPair(p);
        }
        return datasets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Pair p : this.data) {
            sb.append("\n").append("x: ").append(p.getXVector().toString()).append("y: ").append(p.getYVector().toString());
        }
        return sb.toString();
    }

}
