package model;

public class WeightedEdge extends Edge {
    private double weight;

    public WeightedEdge(Vertex endpointsA, Vertex endpointB, double weight) {
        super(endpointsA, endpointB);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
