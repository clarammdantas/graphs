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
import java.util.Stack;

public class GraphLibrary {
	
	private static final double INF_WEIGHT = 1e10;
	
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
    
    public static String bellmanFord(Graph<WeightedEdge> g, Vertex source, Vertex target) throws GraphLibraryException {
    	Vertex parent[] = new Vertex[g.getNumberOfVertices() + 1];
    	double distance[] = new double[g.getNumberOfVertices() + 1];
    	
    	for(int v = 1; v <= g.getNumberOfVertices(); v++) {
    		distance[v] = INF_WEIGHT;
    	}
    	
    	distance[source.getNumber()] = 0;
    	
    	for(int i = 1; i < g.getNumberOfVertices(); i++) {
	    	for(WeightedEdge edge : g.getEdges()) {
	    		relax(edge.getEndpointA(), edge.getEndpointB(), edge.getWeight(), parent, distance);
	    		relax(edge.getEndpointB(), edge.getEndpointA(), edge.getWeight(), parent, distance);
	    	}
    	}
    	
    	if(distance[target.getNumber()] == INF_WEIGHT) {
    		throw new GraphLibraryException("Vertex is not reachable");
    	}
    	
    	for(WeightedEdge edge : g.getEdges()) {
    		double dA = distance[edge.getEndpointA().getNumber()];
    		double dB = distance[edge.getEndpointB().getNumber()];
    		
    		if(dA > dB + edge.getWeight() || dB > dA + edge.getWeight()) {
    			throw new GraphLibraryException("Graph has a negative cycle");
        	}
    	}
    	
    	Stack<Vertex> path = new Stack<Vertex>();
    	while(!target.equals(source)) {
    		path.push(target);
    		target = parent[target.getNumber()];
    	}
    	
    	String pathStr = Integer.toString(source.getNumber());
    	while(!path.empty()) {
    		pathStr = " " + Integer.toString(path.pop().getNumber());
    	}
    	
    	return pathStr;
    }
    
    private static void relax(Vertex A, Vertex B, double weight, Vertex parent[], double distance[]) {
    	if(distance[B.getNumber()] > distance[A.getNumber()] + weight) {
    		distance[B.getNumber()] = distance[A.getNumber()] + weight;
    		parent[B.getNumber()] = A;
    	}
	}

	public static String shortestPath(Graph<Edge> g, Vertex source, Vertex target) throws GraphLibraryException {
    	Graph<WeightedEdge> newGraph = new Graph<WeightedEdge>(g.getNumberOfVertices());
    	
    	for(Edge edge : g.getEdges()) {
    		double newWeight;
    		
    		if(edge instanceof WeightedEdge) {
    			newWeight = ((WeightedEdge) edge).getWeight();
    		} else {
    			newWeight = 1.0;
    		}
    		
    		WeightedEdge newEdge = new WeightedEdge(edge.getEndpointA(), edge.getEndpointB(), newWeight);
    		newGraph.addEdge(newEdge);
    	}
    	
    	return bellmanFord(newGraph, source, target);
    }
}
