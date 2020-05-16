import tarantula.activationFunctions.Sigmoid;
import tarantula.activationFunctions.ReLU;
import tarantula.data.Dataset;
import tarantula.layers.FullyConnectedLayer;
import tarantula.matrices.Vector;
import tarantula.neuralNetwork.NeuralNetwork;

public class Test {

    public static void main(String[] args) throws Exception {

        // build network
        NeuralNetwork nn = new NeuralNetwork();
        nn.addLayer(new FullyConnectedLayer(2, 4, new Sigmoid()));
        nn.addLayer(new FullyConnectedLayer(4, 1, new ReLU()));

        // get result
        Dataset data = new Dataset();
        data.readFile("./src/test.csv", ",", 2);
        // data.normalizeData();
        System.out.println(data.toString());

        // train nn
        nn.trainSGD(data, 1000, 0.2);

        System.out.println(nn.toString());

        // predict
        Vector input = Vector.fromArray(new double[]{1,1});
        System.out.println("Prediction: " + nn.predict(input));

    }

}
