package com.company;

public class NeuralNetwork {
    Layer[] layers;
    double learnRate;
    double momentum;

    public NeuralNetwork(int[] sizesOfLayers, double learnRate, double momentum) {
        this.layers = new Layer[sizesOfLayers.length];
        this.learnRate = learnRate;
        this.momentum = momentum;

        layers[0] = new Layer(sizesOfLayers[0]);

        for(int i = 1; i < sizesOfLayers.length; i++) {
            layers[i] = new Layer(sizesOfLayers[i], layers[i - 1]);
        }
    }

    void train(int[][] trainSets, int[] expected, int maxEpoch) {
        for (int epoch = 0; epoch < maxEpoch; epoch++) {
            double[] predict = new double[trainSets.length];

            for (int trainSet = 0; trainSet < trainSets.length; trainSet++) {
                fit(trainSets[trainSet]);
                predict[trainSet] = predict();

                backPropagate(expected[trainSet], predict[trainSet]);
            }

            double mse = MSE(expected, predict);
            if (epoch % 5000 == 0) System.out.println("epoch: " + epoch + ", error: " + mse);
        }
    }

    public void fit(int[] input) {
        for (int i = 0; i < input.length; i++) {
            layers[0].neurons[i].val = input[i];
        }
    }

    double predict() {
        for (int i = 1; i < layers.length; i++) {
            for (Neuron neuron: layers[i].neurons) {
                neuron.calc();
                neuron.sigmoid();
            }
        }

        return layers[layers.length - 1].neurons[0].val;
    }

    void backPropagate(double expected, double predict) {
        layers[layers.length - 1].neurons[0].delta = (expected - predict) * ((1 - predict) * predict);

        for (int i = layers.length - 2; i >= 0; i--) {
            for (int j = layers[i].neurons.length - 1; j >= 0; j--) {
                double sum = 0;
                for (int g = 0; g < layers[i + 1].neurons.length; g++) {
                    sum += layers[i].neurons[j].synapsesTo[g].val * layers[i + 1].neurons[g].delta;

                    double gradient = layers[i + 1].neurons[g].delta * layers[i].neurons[j].val;

                    if (g > 0) {
                        layers[i].neurons[j].synapsesTo[g].delta = learnRate * gradient + momentum * layers[i].neurons[j].synapsesTo[g - 1].delta;
                    } else {
                        layers[i].neurons[j].synapsesTo[g].delta = learnRate * gradient;
                    }

                    layers[i].neurons[j].synapsesTo[g].val += layers[i].neurons[j].synapsesTo[g].delta;
                }

                layers[i].neurons[j].delta = ((1 - layers[i].neurons[j].val) * layers[i].neurons[j].val) * sum;
            }
        }
    }

    double MSE(int expected[], double predict[]) {
        double sum = 0;
        for (int i = 0; i < expected.length; i++) {
            sum += Math.pow(expected[i] - predict[i], 2);
        }

        return sum / expected.length;
    }
}
