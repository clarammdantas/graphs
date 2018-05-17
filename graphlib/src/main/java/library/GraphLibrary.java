package library;

import exception.GraphLibraryException;
import model.Edge;
import model.Graph;
import model.WeightedEdge;
import util.GraphReader;

import java.io.FileNotFoundException;

public class GraphLibrary {
    public Graph<Edge> readGraph(String filePath) throws GraphLibraryException, FileNotFoundException {
        GraphReader graphReader = new GraphReader(filePath);

        int numberOfVertices = graphReader.nextInt();
        Graph<Edge> graph = new Graph<Edge>(numberOfVertices);

        while (graphReader.hasNextLine()) {
            Edge edge = graphReader.nextEdge();
            graph.addEdge(edge);
        }

        return graph;
    }

    public Graph<WeightedEdge> readWeightedGraph(String filePath) throws GraphLibraryException, FileNotFoundException {
        GraphReader graphReader = new GraphReader(filePath);

        int numberOfVertices = graphReader.nextInt();
        Graph<WeightedEdge> graph = new Graph<WeightedEdge>(numberOfVertices);

        while (graphReader.hasNextLine()) {
            WeightedEdge edge = graphReader.nextWeightedEdge();
            graph.addEdge(edge);
        }

        return graph;
    }
    
    public int getVertexNumber(Graph<Edge> graph) {
    	return graph.getNumberOfVertices() ;
    }
    
    public int getEdgeNumber(Graph<Edge> graph) {
    	return graph.getEdges().size();
    }
    
    public int getMeanEdge(Graph<Edge> graph) {
    	int[] edgeByVertex = new int[getVertexNumber(graph)];
    	
    	for (Edge edges: graph.getEdges()) {
    		edgeByVertex[edges.getEndpointA().getNumber() - 1]++;
    		edgeByVertex[edges.getEndpointB().getNumber() - 1]++;
    	}
    	int sumDegrees = 0;
    	
    	for (int degrees: edgeByVertex) {
    		sumDegrees += degrees;
    	}
    	
    	return sumDegrees / getVertexNumber(graph);
    }
}
