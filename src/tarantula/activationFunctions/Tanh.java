package tarantula.activationFunctions;

public class Tanh implements ActivationFunction {

    @Override
    public double feed(double val) {
        return Math.tanh(val);
    }

    @Override
    public double derivative(double val) {
        return 1 - val * val;
    }

}
