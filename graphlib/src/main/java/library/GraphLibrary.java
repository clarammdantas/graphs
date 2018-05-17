package library;

import com.sun.org.apache.xpath.internal.operations.Bool;
import exception.GraphLibraryException;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import util.GraphReader;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Edge> edges = g.getEdges();
        Map<Vertex, Boolean> visited = setVisited(edges);

        for (Edge edge : edges) {
            if (!visited.get(edge.getEndpointA())) {
                DFSAux(g, visited, edge.getEndpointA(), 0);
            }
        }

    	return "incompleto";
    }

    private static void DFSAux(Graph<Edge> g, Map<Vertex, Boolean> visited,
                               Vertex vertex, Integer level) {

        visited.put(vertex, new Boolean(true));

        List<Vertex> neighbours = g.getNeighbours(vertex);
        for (Vertex neighbourVertex : neighbours) {
            if (!visited.get(neighbourVertex)) {
                DFSAux(g, visited, vertex, level + 1);
            }
        }
    }

    private static Map<Vertex, Boolean> setVisited(List<Edge> edges) {
        Map<Vertex, Boolean> visited = new HashMap();
        for (Edge edge : edges) {
            visited.put(edge.getEndpointA(), new Boolean(false));
        }

        return visited;
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
