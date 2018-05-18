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

    /**
     * Get the adjacent vertices of a given vertex.
     *
     * @param vertex
     * @return List of vertices that are adjacent to the given vertex
     */
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

    /**
     *
     * @return Total number of vertices in the graph.
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     *
     * @return A set with all vertices in the graph.
     */
    public Set<Vertex> getVertices() {
        Set<Vertex> vertices = new HashSet();
        for (T edge : edges) {
            vertices.add(edge.getEndpointA());
            vertices.add(edge.getEndpointB());
        }

        return vertices;
    }

    public int getNumberOfEdges() {
        return edges.size();
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

    /**
     * Check whether a vertex value is valid or not. To be valid, the value must
     * be between 1 and the total number of vertices, inclusive.
     * @param vertex
     * @return true if a vertex value is valid, false otherwise.
     */
    private boolean validVertexNumber(Vertex vertex) {
        return vertex.getNumber() > 0 && vertex.getNumber() <= numberOfVertices;
    }

    /**
     * Add an edge into the graph.
     * @param edge
     * @throws GraphLibraryException
     */
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
