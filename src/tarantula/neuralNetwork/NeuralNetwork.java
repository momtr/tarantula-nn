package tarantula.neuralNetwork;

import tarantula.data.Dataset;
import tarantula.data.Pair;
import tarantula.layers.Layer;
import tarantula.matrices.Matrix;
import tarantula.matrices.Vector;

import java.util.ArrayList;

public class NeuralNetwork {

    private ArrayList<Layer> layers;

    public NeuralNetwork() {
        this.layers = new ArrayList<Layer>();
    }

    public void addLayer(Layer layer) throws Exception {
        if(layer == null)
            throw new Exception("Layer should not be null");
        this.layers.add(layer);
    }

    public Matrix feedForward(Matrix input) {
        for(Layer l : this.layers) {
            input = l.feedForward(input);
        }
        return input;
    }

    public ArrayList<Matrix> feedForwardWithHistory(Matrix input) {
        ArrayList<Matrix> history = new ArrayList<Matrix>();
        history.add(input);
        for(Layer l : this.layers) {
            input = l.feedForward(input);
            history.add(input);
        }
        return history;
    }

    public void train(Dataset data) {
        this.train(data, 100000, 0.2);
    }

    public void train(Dataset data, int epochs, double lr) {
        for(int epoch = 0; epoch < epochs; epoch++) {
            for(Pair p : data.getPairs()) {
                Vector x = p.getXVector();
                Vector y = p.getYVector();

                // (1) predict
                ArrayList<Matrix> history = this.feedForwardWithHistory(x);

                // (2) calc the error
                Matrix modelY = history.get(history.size() - 1);
                Matrix error = Matrix.subtract(y, modelY);

                // (3) gradient descent and backprop
                for(int i = this.layers.size() - 1; i >= 0; i--) {
                    error = this.layers.get(i).train(error, history.get(i), history.get(i + 1), lr);
                }

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("NeuralNetwork: \n\n");
        for(Layer l : this.layers) {
            ret.append(l.toString()).append("\n\n");
        }
        return ret.toString();
    }
}
