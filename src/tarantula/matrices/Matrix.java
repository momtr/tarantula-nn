package tarantula.matrices;

import tarantula.activationFunctions.ActivationFunction;

import java.util.ArrayList;

public class Matrix {

    private int rows;
    private int cols;
    private ArrayList<ArrayList<Double>> data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i < rows; i++) {
            ArrayList<Double> current = new ArrayList<Double>();
            for(int j = 0; j < cols; j++) {
                current.add(Math.random());
            }
            this.data.add(current);
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if(a.getCols() != b.getRows())
            throw new Error("Cols of a must match rows of b");
        Matrix ret = new Matrix(a.getRows(), b.getCols());
        for(int i = 0; i < a.getRows(); i++) {
            for(int j = 0; j < b.getCols(); j++) {
                double sum = 0;
                for(int k = 0; k < a.getCols(); k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                ret.set(i, j, sum);
            }
        }
        return ret;
    }

    public static Matrix multiplyDirectional(Matrix a, Matrix b) {
        if(a.getCols() != 1 || a.getCols() != b.getCols() || a.getRows() != b.getRows())
            throw new Error("Matrices' shapes must be the same");
        Matrix ret = new Matrix(a.getRows(), a.getCols());
        for(int i = 0; i < a.getRows(); i++) {
            for(int j = 0; j < a.getCols(); j++) {
                ret.set(i, j, a.get(i, j) * b.get(i, j));
            }
        }
        return ret;
    }

    public void add(Matrix a) {
        if(a.getCols() != this.getCols() || a.getRows() != this.getRows())
            throw new Error("rows of a must match rows and cols of a must match cols");
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                this.set(i, j, this.get(i, j) + a.get(i, j));
            }
        }
    }

    public void subtract(Matrix a) {
        if(a.getCols() != this.getCols() || a.getRows() != this.getRows())
            throw new Error("rows of a must match rows and cols of a must match cols");
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                this.set(i, j, this.get(i, j) - a.get(i, j));
            }
        }
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if(a.getCols() != b.getCols() || a.getRows() != b.getRows())
            throw new Error("rows of a must match rows of b and cols of a must match cols of b");
        Matrix ret = new Matrix(a.getRows(), a.getCols());
        for(int i = 0; i < a.getRows(); i++) {
            for(int j = 0; j < a.getCols(); j++) {
                ret.set(i, j, a.get(i, j) - b.get(i, j));
            }
        }
        return ret;
    }

    public void multiply(double m) {
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                this.set(i, j, m * this.get(i, j));
            }
        }
    }

    public void map(ActivationFunction func) {
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                this.set(i, j, func.feed(this.get(i, j)));
            }
        }
    }

    public void mapDerivative(ActivationFunction func) {
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                this.set(i, j, func.derivative(this.get(i, j)));
            }
        }
    }

    public static Matrix transpose(Matrix a) {
        Matrix ret = new Matrix(a.getCols(), a.getRows());
        for(int i = 0; i < a.getRows(); i++) {
            for(int j = 0; j < a.getCols(); j++) {
                ret.set(j, i, a.get(i, j));
            }
        }
        return ret;
    }

    ArrayList<ArrayList<Double>> getData() {
        return this.data;
    }

    public double get(int row, int col) {
        return this.data.get(row).get(col);
    }

    public void set(int row, int col, double val) {
        this.data.get(row).set(col, val);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("[");
        for(ArrayList<Double> ar : this.data) {
            ret.append("\n\t[");
            for(double i : ar) {
                ret.append(i).append(", ");
            }
            ret.append("]");
        }
        return ret.append("\n]").toString();
    }

    public Matrix clone() {
        Matrix ret = new Matrix(this.getRows(), this.getCols());
        for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                ret.set(i, j, this.get(i, j));
            }
        }
        return ret;
    }
}