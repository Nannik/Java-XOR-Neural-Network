package com.company;

public class Synapse {
    double val;
    double delta;
    Neuron prevNeuron;
    Neuron nextNeuron;

    public Synapse(double val, double delta) {
        this.val = val;
        this.delta = delta;
    }
}
