package activationFunction;

public class Sigmoid implements ActivationFunction {

    @Override
    public double feed(double val) {
        return (1 / (1 + Math.exp(-val)));
    }

    @Override
    public double derivative(double val) {
        return val * (1 - val);
    }

}
