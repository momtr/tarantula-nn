import tarantula.activationFunctions.ReLU;
import tarantula.activationFunctions.Sigmoid;
import tarantula.activationFunctions.Tanh;
import tarantula.data.Dataset;
import tarantula.layers.FullyConnectedLayer;
import tarantula.matrices.Matrix;
import tarantula.matrices.Vector;
import tarantula.neuralNetwork.NeuralNetwork;

public class ExampleHouses {

    public static void main(String[] args) throws Exception {

        // get data set
        // Gewinn (y), Preis in Mio (x1), Quadratmeter (x2)
        Dataset dataset = new Dataset();
        dataset.readFile("./src/hotels.csv");

        // normalize data
        dataset.normalizeData();

        // build network
        NeuralNetwork nn = new NeuralNetwork();
        nn.addLayer(new FullyConnectedLayer(2, 6, new Sigmoid()));
        nn.addLayer(new FullyConnectedLayer(6, 3, new Tanh()));
        nn.addLayer(new FullyConnectedLayer(3, 1, new Tanh()));

        // train network
        nn.train(dataset, 100, 0.1);

        // test it
        Vector input = Vector.fromArray(new double[]{ 18.45, 3358.0 });
        Matrix normalized = dataset.normalizeMatrix(input);
        System.out.println("Normalized: " + normalized.toString());
        Matrix prediction = nn.feedForward(normalized);
        Matrix denormalized = dataset.denormalizeMatrix(prediction);
        System.out.println("Prediction: " + prediction.toString());

    }

}
