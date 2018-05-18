package model;

/**
 * Bidirectional edge with two endpoints
 */
public class Edge {
    private Vertex endpointA;
    private Vertex endpointB;

    public Edge(Vertex endpointA, Vertex endpointB) {
        this.endpointA = endpointA;
        this.endpointB = endpointB;
    }

    public Vertex getEndpointA() {
        return endpointA;
    }

    public void setEndpointA(Vertex endpointA) {
        this.endpointA = endpointA;
    }

    public Vertex getEndpointB() {
        return endpointB;
    }

    public void setEndpointB(Vertex endpointB) {
        this.endpointB = endpointB;
    }

    @Override
    public boolean equals(Object o) {
        boolean equals;
        if (o == null || !(o instanceof Edge)) {
            equals = false;
        } else {
            Edge edge = (Edge) o;
            equals = endpointA.equals(edge.getEndpointA()) && endpointB.equals(edge.getEndpointB());
        }
        return equals;
    }
}
