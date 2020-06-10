package tarantula.layers;

import tarantula.activationFunctions.ActivationFunction;
import tarantula.matrices.Matrix;
import tarantula.matrices.Vector;

public abstract class Layer {

    private int[] shape;
    private ActivationFunction activationFunction;
    private Matrix weights;
    private Matrix biases;

    public Layer(int input, int output, ActivationFunction activationFunction) {
        this.shape = new int[]{input, output};
        this.activationFunction = activationFunction;
        this.weights = new Matrix(output, input);
        this.biases = new Vector(output);
    }

    public int[] getShape() {
        return shape;
    }

    public Matrix getWeights() {
        return weights;
    }

    public Matrix getBiases() {
        return biases;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Layer: \n");
        ret.append("weights: ").append(this.weights.toString());
        ret.append("biases: ").append(biases.toString());
        return ret.toString();
    }

    public abstract Matrix feedForward(Matrix input);
    public abstract Matrix train(Matrix error, Matrix input, Matrix output, double learningRate);
    public abstract Matrix calcGradient(Matrix error, Matrix output, double learningRate);
    public abstract Matrix calcWeightGradient(Matrix gradient, Matrix input);
    public abstract void updateWeights(Matrix gradient);
    public abstract void updateBiases(Matrix biases);
    public abstract Matrix backprop(Matrix error, Matrix output);

}
