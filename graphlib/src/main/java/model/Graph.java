package model;

import exception.GraphLibraryException;

import java.util.ArrayList;
import java.util.List;

public class Graph <T extends Edge> {
    int numberOfVertices;
    private List<T> edges;

    private String ADJACENCY_MATRIX = "AM";
    private String LS = System.lineSeparator();


    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.edges = new ArrayList<T>();
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

	public String representation(String type) {
            if (type == ADJACENCY_MATRIX ) {
                return AMRepresentation();
            } else  {
                return ALRepresentation();
            }
    }
    

    private String AMRepresentation() {
        String representatiton = "";
        int numberOfVertices = getNumberOfVertices();
        representatiton += makeFirstRowAMRepresentation(numberOfVertices);
        int[][] matrix = createAdjacencyMatrix();
        for (int row = 0; row < numberOfVertices ; row++) {
            String string_row = "" + (row + 1);
            for (int column = 0; column < numberOfVertices; column++) {
                string_row += " " + matrix[row][column];
            } 
            string_row+= LS;
            representatiton+= string_row;
        }
        return representatiton;
	}

	private String ALRepresentation() {
        String representatiton = "";
        int numberOfVertices = getNumberOfVertices();
        int[][] matrix = createAdjacencyMatrix();
        for (int column = 0; column < numberOfVertices ; column++) {
            String string_row = column +1 + " -";
            for (int row = 0; row < numberOfVertices; row++) {
                if (matrix[row][column] != 0) {
                    string_row += " " + (row + 1);
                }

            } 
            string_row+= LS;
            representatiton+= string_row;
        }
        return representatiton;
    }
    

    private String makeFirstRowAMRepresentation(int numberOfVertices) {
        String firstRow = " ";
        for (int i = 0; i < numberOfVertices; i++) {
            int vertex = i + 1;
            firstRow += " " + vertex;
        }
        firstRow += LS;
        return firstRow;
    }
    

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
    
}
