import tarantula.activationFunctions.Sigmoid;
import tarantula.activationFunctions.Tanh;
import tarantula.data.Dataset;
import tarantula.layers.FullyConnectedLayer;
import tarantula.matrices.Vector;
import tarantula.neuralNetwork.NeuralNetwork;

public class Main {

    public static void main(String[] args) throws Exception {

        // build network
        NeuralNetwork nn = new NeuralNetwork();
        nn.addLayer(new FullyConnectedLayer(2, 2, new Sigmoid()));
        nn.addLayer(new FullyConnectedLayer(2, 1, new Tanh()));

        // get result
        Dataset data = new Dataset();
        data.addExample(new double[]{1,1}, new double[]{0});
        data.addExample(new double[]{0,0}, new double[]{0});
        data.addExample(new double[]{1,0}, new double[]{1});
        data.addExample(new double[]{0,1}, new double[]{1});

        // train model (mini-batch gradient descnet)
        nn.trainMBGD(data, 2, 10000, 0.2);

        // predict
        Vector input = Vector.fromArray(new double[]{1,0});
        System.out.println("Prediction: " + nn.predict(input));

    }

}
