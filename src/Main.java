import activationFunction.ReLU;
import activationFunction.Sigmoid;
import data.Dataset;
import layers.FullyConnectedLayer;
import matrix.Vector;
import neuralNetwork.NeuralNetwork;

public class Main {

    public static void main(String[] args) throws Exception {
        // build network
        NeuralNetwork nn = new NeuralNetwork();
        nn.addLayer(new FullyConnectedLayer(2, 3, new ReLU()));
        nn.addLayer(new FullyConnectedLayer(3, 1, new Sigmoid()));

        // get result
        Dataset data = new Dataset();
        data.addExample(new double[]{0,0}, new double[]{0});
        data.addExample(new double[]{0,1}, new double[]{1});
        data.addExample(new double[]{1,0}, new double[]{1});
        data.addExample(new double[]{1,1}, new double[]{0});
        nn.train(data, 100000, 0.2);

        // System.out.println(nn.toString());

        // predict
        Vector input = Vector.fromArray(new double[]{1,0});
        System.out.println("Prediction: " + nn.feedForward(input));

    }

}
