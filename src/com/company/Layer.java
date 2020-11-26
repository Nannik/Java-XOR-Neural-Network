package com.company;

public class Layer {
    Neuron[] neurons;

    public Layer(int size) {
        this.neurons = new Neuron[size];

        for (int i = 0; i < size; i++) {
            this.neurons[i] = new Neuron();
        }
    }

    public Layer(int size, Layer prevLayer) {
        this.neurons = new Neuron[size];

        for (int i = 0; i < size; i++) {
            this.neurons[i] = new Neuron();
            this.neurons[i].synapsesFrom = new Synapse[prevLayer.neurons.length];
        }

        for (int i = 0; i < prevLayer.neurons.length; i++) {
            prevLayer.neurons[i].synapsesTo = new Synapse[size];

            for (int j = 0; j < size; j++) {
                Synapse synapse = new Synapse(Math.random() * 2 - 1, 0);
                synapse.nextNeuron = this.neurons[j];
                synapse.prevNeuron = prevLayer.neurons[i];

                prevLayer.neurons[i].synapsesTo[j] = synapse;
                this.neurons[j].synapsesFrom[i] = synapse;
            }
        }
    }
}
