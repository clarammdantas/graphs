package library;

import exception.GraphLibraryException;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import util.GraphReader;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

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
    
    public static String DFS(Graph<Edge> g, Vertex v) {
    	return "incompleto";
    }
    
    public static String MST(Graph<WeightedEdge> g) throws GraphLibraryException {
    	DisjointSet ds = new DisjointSet();
    	List<WeightedEdge> edges = g.getEdges();
    	Collections.sort(edges);
    	
    	Graph<Edge> mst = new Graph<Edge>(g.getNumberOfVertices());
    	for(WeightedEdge edge : edges) {
    		Vertex A = edge.getEndpointA();
    		Vertex B = edge.getEndpointB();
    		
    		if(ds.mergeSets(A.getNumber(), B.getNumber())) {
    			mst.addEdge(new Edge(A, B));
    		}
    	}
    	
    	return DFS(mst, new Vertex(1));
    }
}
