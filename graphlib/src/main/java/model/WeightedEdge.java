	package model;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
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
    
    public int compareTo(WeightedEdge o) {
    	return Double.compare(getWeight(), o.getWeight());
    }
}
