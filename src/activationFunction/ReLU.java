package activationFunction;

public class ReLU implements ActivationFunction {

    @Override
    public double feed(double val) {
        return ((val <= 0) ? 0 : val);
    }

    @Override
    public double derivative(double val) {
        return ((val <= 0) ? 0 : 1);
    }
}
