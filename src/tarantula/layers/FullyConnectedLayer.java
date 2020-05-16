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

        // (1) calc the gradient * lr
        Matrix gradients = output.clone();
        gradients.mapDerivative(super.getActivationFunction());
        gradients = Matrix.multiplyDirectional(gradients, error);
        gradients.multiply(2 * learningRate);
        Matrix input_t = Matrix.transpose(input);
        Matrix weight_deltas = Matrix.multiply(gradients, input_t);

        // (2) update weights and biases
        super.getWeights().add(weight_deltas);
        super.getBiases().add(gradients);
        Matrix weights_t = Matrix.transpose(super.getWeights());

        // (3) return the new error
        return Matrix.multiply(weights_t, error);

    }

}
