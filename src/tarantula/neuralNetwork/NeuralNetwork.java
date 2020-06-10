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

    public Matrix predict(Matrix input) {
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
        this.trainSGD(data, 100000, 0.2);
    }

    // stochastic gradient descent
    public void trainSGD(Dataset data, int epochs, double lr) {
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

    // mini-batch gradient descent
    public void trainMBGD(Dataset data, int batchSize, int epochs, double lr) {
        if(batchSize > data.size() || batchSize <= 0)
            throw new IllegalArgumentException("batchSize must be in range [1; length_of_dataset]");
        for(int epoch = 0; epoch < epochs; epoch++) {
            for(int i = 0; i < data.getPairs().size() - 1; i += batchSize) {

                ArrayList<Matrix> weightGradients = new ArrayList<Matrix>();
                ArrayList<Matrix> biasGradients = new ArrayList<Matrix>();

                for(int j = i; j < (batchSize + i); j++) {

                    if(j < data.size()) {

                        Vector x = data.getPairs().get(j).getXVector();
                        Vector y = data.getPairs().get(j).getYVector();

                        // (1) calc the error and the history
                        ArrayList<Matrix> history = this.feedForwardWithHistory(x);
                        Matrix modelY = history.get(history.size() - 1);
                        Matrix error = Matrix.subtract(y, modelY);

                        // (2) calc the gradients for each layer and add them to the lists
                        for (int k = this.layers.size() - 1; k >= 0; k--) {
                            Matrix gradients = this.layers.get(k).calcGradient(error, history.get(k + 1), lr);
                            Matrix weightGrads = this.layers.get(k).calcWeightGradient(gradients, history.get(k));
                            error = this.layers.get(k).backprop(error, history.get(k));
                            if (j == i) {
                                weightGradients.add(weightGrads);
                                biasGradients.add(gradients);
                            } else {
                                int index = this.layers.size() - 1 - k;
                                weightGradients.get(index).add(weightGrads);
                                biasGradients.get(index).add(gradients);
                            }
                        }

                    }

                }

                // (3) calc the average and update the gradients
                for(int k = this.layers.size() - 1; k >= 0; k--) {
                    int index = this.layers.size() - 1 - k;
                    Matrix currentWeightGradient = weightGradients.get(index);
                    Matrix currentBiasGradient = biasGradients.get(index);
                    currentWeightGradient.multiply(1 / (double) this.layers.size());
                    currentBiasGradient.multiply(1 / (double) this.layers.size());
                    this.layers.get(k).updateBiases(currentBiasGradient);
                    this.layers.get(k).updateWeights(currentWeightGradient);
                }

            }
        }
    }

    public double meanSquaredError(Dataset t) {
        double sum = 0;
        ArrayList<Pair> data = t.getPairs();
        for(Pair p : data) {
            Vector x = p.getXVector();
            Vector y = p.getYVector();
            Matrix prediction = this.predict(x);
            Matrix errors = Matrix.subtract(y, prediction);
            sum += errors.sumOfSquares();
        }
        return sum / t.size();
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
