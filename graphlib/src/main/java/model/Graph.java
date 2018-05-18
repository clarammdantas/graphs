package model;

import exception.GraphLibraryException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enumeration.Representation;

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
	
	/**
	 * Get the correct representation of the current graph for the choosen type
	 * @param type
	 * 
	 */
	public String representation(String type) {
		boolean isWeighted = isWeighted();
		if (isWeighted) {
			if (type == Representation.ADJACENCY_MATRIX.getType()) {
				return AMWeightedRepresentation();
			} else  {
				return ALWeightedRepresentation();
			}
		} else {
			if (type == Representation.ADJACENCY_MATRIX.getType()) {
				return AMRepresentation();
			} else  {
				return ALRepresentation();
			}
		}
	}

/**
 * Adcency List representation for a weighted graph
 */
private String ALWeightedRepresentation() {
	String representation = "";
	int numberOfVertices = getNumberOfVertices();
	for (int vertexA = 1; vertexA <= numberOfVertices ; vertexA++) {
		String string_row = vertexA + " -";
			for (WeightedEdge edge: (List<WeightedEdge>) getEdges()) {
				if (edge.getEndpointA().getNumber() == vertexA || edge.getEndpointB().getNumber() == vertexA) {
					int vertex = getOpositeVertex(edge, vertexA);
					string_row += " " + vertex + "(" + edge.getWeight() + ")";
				}
			}
			
		string_row += "\n";
		representation += string_row;
	}
	return representation;
}

private int getOpositeVertex(Edge edge, int vertex) {
	if (vertex == edge.getEndpointA().getNumber()) {
		return edge.getEndpointB().getNumber();
	}
	return edge.getEndpointA().getNumber();
}

/**
 * Adjacency Matrix representation for a weighted graph
 */
private String AMWeightedRepresentation() {
	String representation = "";
	int numberOfVertices = getNumberOfVertices();
	representation += makeFirstRowAMRepresentation(numberOfVertices);
	double[][] matrix = createWheightedAdjacencyMatrix();
	for (int row = 0; row < numberOfVertices ; row++) {
		String string_row = "" + (row + 1);
		for (int column = 0; column < numberOfVertices; column++) {
			string_row += " " + matrix[row][column];
		} 
		string_row += "\n";
		representation += string_row;
	}
	return representation;
}

/**
 * Returns is a graph is weighted or not
 */
private boolean isWeighted() {
	return (getEdges().get(0) instanceof WeightedEdge);
}

/**
 * Adjacency Matrix representation for a non-weighted graph
 */
private String AMRepresentation() {
	String representation = "";
	int numberOfVertices = getNumberOfVertices();
	representation += makeFirstRowAMRepresentation(numberOfVertices);
	int[][] matrix = createAdjacencyMatrix();
	for (int row = 0; row < numberOfVertices ; row++) {
		String string_row = "" + (row + 1);
		for (int column = 0; column < numberOfVertices; column++) {
			string_row += " " + matrix[row][column];
		} 
		string_row += "\n";
		representation += string_row;
	}
	return representation;
}

/**
 * Adjacency List representation for a non-weighted graph
 */
private String ALRepresentation() {
	String representation = "";
	int numberOfVertices = getNumberOfVertices();
	int[][] matrix = createAdjacencyMatrix();
	for (int column = 0; column < numberOfVertices ; column++) {
		String string_row = column +1 + " -";
		for (int row = 0; row < numberOfVertices; row++) {
			if (matrix[row][column] != 0) {
				string_row += " " + (row + 1);
			}

		} 
		string_row += "\n";
		representation += string_row;
	}
	return representation;
}


private String makeFirstRowAMRepresentation(int numberOfVertices) {
	String firstRow = " ";
	for (int i = 0; i < numberOfVertices; i++) {
		int vertex = i + 1;
		firstRow += " " + vertex;
	}
	firstRow += "\n";
	return firstRow;
}

/**
 * Create Adjacency Matrix representation of a non-weighted Graph
 */
private int[][] createAdjacencyMatrix() {
	int numberOfVertices = getNumberOfVertices();
	int[][] matrix = new int[numberOfVertices][numberOfVertices];
	for (int i = 0;i < numberOfVertices;i++) {
		for ( int j = 0; j < numberOfVertices ; j++) {
			matrix[i][j] = 0;
		}
	}
	List<Edge> edges =  (List<Edge>) getEdges();
	for (Edge edge : edges) {
		int vertexA = edge.getEndpointA().getNumber() - 1;
		int vertexB = edge.getEndpointB().getNumber() - 1;
		matrix[vertexA][vertexB] = 1;
		matrix[vertexB][vertexA] = 1;
	}
	return matrix;
}

/**
 * Create Adjacency Matrix representation of a weighted Graph
 */
private double[][] createWheightedAdjacencyMatrix() {
	int numberOfVertices = getNumberOfVertices();
	double[][] matrix = new double[numberOfVertices][numberOfVertices];
	for (int i = 0;i < numberOfVertices;i++) {
		for ( int j = 0; j < numberOfVertices ; j++) {
			matrix[i][j] = 0;
		}
	}
	List<WeightedEdge> edges =  (List<WeightedEdge>) getEdges();
	for (WeightedEdge edge : edges) {
		int vertexA = edge.getEndpointA().getNumber() - 1;
		int vertexB = edge.getEndpointB().getNumber() - 1;
		matrix[vertexA][vertexB] = edge.getWeight();
		matrix[vertexB][vertexA] = edge.getWeight();
	}
	return matrix;
}
}
