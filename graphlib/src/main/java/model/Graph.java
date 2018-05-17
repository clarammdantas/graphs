package model;

import exception.GraphLibraryException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph <T extends Edge> {
    int numberOfVertices;
    private List<T> edges;

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.edges = new ArrayList<T>();
    }

    public List<Vertex> getNeighbours(Vertex vertex) {
        List<Vertex> neighbours = new ArrayList();
        for (T edge : edges) {
            if (edge.getEndpointA().equals(vertex)) {
                neighbours.add(edge.getEndpointB());
            } else if (edge.getEndpointB().equals(vertex)) {
                neighbours.add(edge.getEndpointA());
            }
        }

        return neighbours;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public Set<Vertex> getVertices() {
        Set<Vertex> vertices = new HashSet();
        for (T edge : edges) {
            vertices.add(edge.getEndpointA());
            vertices.add(edge.getEndpointB());
        }

        return vertices;
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
