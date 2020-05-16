package tarantula.activationFunctions;

public interface ActivationFunction {

    public double feed(double val);
    public double derivative(double val);

}
