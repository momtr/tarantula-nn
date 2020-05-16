package tarantula.layers;

import tarantula.activationFunctions.ActivationFunction;
import tarantula.matrices.Matrix;

public class FullyConnectedLayer extends Layer {

    public FullyConnectedLayer(int input, int output, ActivationFunction activationFunction) {
        super(input, output, activationFunction);
    }

    @Override
    public Matrix feedForward(Matrix input) {
        // af ( input * weights + biases )
        Matrix product = Matrix.multiply(super.getWeights(), input);
        product.add(super.getBiases());
        product.map(super.getActivationFunction());
        return product;
    }

    @Override
    public Matrix train(Matrix error, Matrix input, Matrix output, double learningRate) {

        Matrix gradient = this.calcGradient(error, output, learningRate);
        Matrix weight_gradient = this.calcWeightGradient(gradient, input);
        this.updateWeights(weight_gradient);
        this.updateBiases(gradient);
        return this.backprop(error);

    }

    @Override
    public Matrix calcGradient(Matrix error, Matrix output, double learningRate) {
        Matrix gradients = output.clone();
        gradients.mapDerivative(super.getActivationFunction());
        gradients = Matrix.multiplyDirectional(gradients, error);
        gradients.multiply(2 * learningRate);
        return gradients;
    }

    @Override
    public Matrix calcWeightGradient(Matrix gradient, Matrix input) {
        Matrix input_t = Matrix.transpose(input);
        return Matrix.multiply(gradient, input_t);
    }

    @Override
    public void updateWeights(Matrix gradient) {
        super.getWeights().add(gradient);
    }

    @Override
    public void updateBiases(Matrix biases) {
        super.getBiases().add(biases);
    }

    @Override
    public Matrix backprop(Matrix error) {
        Matrix weights_t = Matrix.transpose(super.getWeights());
        return Matrix.multiply(weights_t, error);
    }


}
