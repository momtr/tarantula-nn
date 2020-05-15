package matrix;

import java.util.ArrayList;

public class Vector extends Matrix {

    public Vector(int rows) {
        super(rows, 1);
    }

    public static Vector fromArray(double[] input) {
        Vector ret = new Vector(input.length);
        for(int i = 0; i < ret.getRows(); i++) {
            ret.set(i, 0, input[i]);
        }
        return ret;
    }

    public static Vector fromArrayList(ArrayList<Double> input) {
        Vector ret = new Vector(input.size());
        for(int i = 0; i < ret.getRows(); i++) {
            ret.set(i, 0, input.get(i));
        }
        return ret;
    }

    public static double[] toArray(Vector a) {
        double[] ret = new double[a.getRows()];
        for(int i = 0; i < a.getRows(); i++) {
            ret[i] = a.get(i, 0);
        }
        return ret;
    }

}
