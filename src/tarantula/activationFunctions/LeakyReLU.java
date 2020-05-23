package tarantula.activationFunctions;

public class LeakyReLU implements ActivationFunction {

    @Override
    public double feed(double val) {
        return ((val > 0) ? val : (0.01 * val));
    }

    @Override
    public double derivative(double val) {
        return ((val > 0) ? 1 : 0.01);
    }

}
