package com.company;

public class Neuron {
    Synapse[] synapsesFrom;
    Synapse[] synapsesTo;
    double val;
    double delta;

    public void sigmoid() {
        val = 1 / (1 + Math.exp(-val));
    }

    public void calc() {
        double sum = 0;
        for (Synapse synapse: synapsesFrom) {
            sum += synapse.prevNeuron.val * synapse.val;
        }
        val = sum;
    }

}
