package com.company;

public class Main {
    static final double LEARN_RATE = 0.05d;
    static final double MOMENTUM = 0.3d;
    static final int MAX_EPOCH = 50000;

    static int[][] input = {
            {0, 0},
            {1, 0},
            {0, 1},
            {1, 1},
    };

    static int[] output = {0, 1, 1, 0};

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[]{2, 3, 1}, LEARN_RATE, MOMENTUM);
        neuralNetwork.train(input, output, MAX_EPOCH);

        int[] test = {1, 1};
        neuralNetwork.fit(test);

        System.out.println("\noutput: " + neuralNetwork.predict());
    }
}
