package layers;

import activationFunction.ActivationFunction;
import matrix.Matrix;

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

        // System.out.println("error: " + error.toString());
        // System.out.println("input: " + input.toString());
        // System.out.println("output: " + output.toString());

        // (1) calc the gradient * lr
        // gradient_lr = 2 * error * f'(output) * lr
        // Matrix eqErrr = error.clone();
        // eqErrr.multiply(2 * learningRate);
        // Matrix eqOutput = output.clone();
        // eqOutput.mapDerivative(super.getActivationFunction());
        // Matrix gradient = Matrix.multiplyDirectional(eqErrr, eqOutput);

        Matrix gradients = output.clone();
        gradients.mapDerivative(super.getActivationFunction());
        gradients = Matrix.multiplyDirectional(gradients, error);
        gradients.multiply(learningRate);

        Matrix input_t = Matrix.transpose(input);
        Matrix weight_deltas = Matrix.multiply(gradients, input_t);
        super.getWeights().add(weight_deltas);
        super.getBiases().add(gradients);

        Matrix weights_t = Matrix.transpose(super.getWeights());
        return Matrix.multiply(weights_t, error);

        // System.out.println("gradient: " + gradient);

        // (2) update weights and biases
        //super.getBiases().add(gradients);
        //super.getWeights().add(Matrix.transpose(Matrix.multiply(input, Matrix.transpose(gradients))));
//
        //// (3) return the new error
        //return Matrix.multiply(Matrix.transpose(super.getWeights()), error);

    }

}
