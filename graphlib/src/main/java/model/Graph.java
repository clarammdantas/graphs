package model;

import exception.GraphLibraryException;

import java.util.ArrayList;
import java.util.List;

public class Graph <T extends Edge> {
    int numberOfVertices;
    private List<T> edges;

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.edges = new ArrayList<T>();
    }

    public List<T> getNeighbour(Vertex vertex) {
        List<T> neighbours = new ArrayList<T>();
        for (T edge : edges) {
            if (edge.getEndpointA().equals(vertex)) {
                neighbours.add(edge);
            }
        }

        return neighbours;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public List<T> getEdges() {
        return edges;
    }

    public void setEdges(List<T> edges) {
        this.edges = edges;
    }

    private boolean validVertexNumber(Vertex vertex) {
        return vertex.getNumber() > 0 && vertex.getNumber() <= numberOfVertices;
    }

    public void addEdge(T edge) throws GraphLibraryException {
        if (validVertexNumber(edge.getEndpointA()) && validVertexNumber(edge.getEndpointB())) {
            if (edges.contains(edge)) {
                throw new GraphLibraryException("Edge Already Exists");
            } else {
                edges.add(edge);
            }
        } else {
            throw new GraphLibraryException("Invalid Vertex Number");
        }
    }
}
