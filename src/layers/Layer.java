package layers;

import activationFunction.ActivationFunction;
import matrix.Matrix;
import matrix.Vector;

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



}
