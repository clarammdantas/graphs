package library;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import exception.GraphLibraryException;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import util.GraphReader;

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
    
    public float getMeanEdge(Graph<Edge> graph) {
    	return (float) 2 * getEdgeNumber(graph) / getVertexNumber(graph);
    }
    
    public boolean connected(Graph<Edge> graph) {
        int numVertex = getVertexNumber(graph);
        int numVertexVisited = 2;
        Vertex firstVertex = new Vertex(1);
        boolean isConnected = true;
       
        if (numVertex > 1) {
            while ((numVertexVisited <= numVertex) && isConnected) {
                Vertex actualVertex = new Vertex(numVertexVisited);
                isConnected = isConect(orderEdges(graph.getEdges()), firstVertex, actualVertex);
                numVertexVisited++;
            }
        }
        return isConnected;
    }
 
    private boolean isConect(List<Edge> edges, Vertex vertex1, Vertex vertex2) {
        for (Edge edge: edges) {
            if(edge.getEndpointA().equals(vertex1) && edge.getEndpointB().equals(vertex2)) {
                return true;
            }else if (edge.getEndpointA().equals(vertex1)) {
                if (vertex2.getNumber() > edge.getEndpointB().getNumber()) {
                    return isConect(edges, edge.getEndpointB(), vertex2);                  
                } else {
                    return isConect(edges, vertex2, edge.getEndpointB());
                   
                }
            }          
        }
        return false;
    }
   
    private List<Edge> orderEdges(List<Edge> edges) {
        List<Edge> result = new ArrayList<Edge>();
        for (Edge edge: edges) {
            if (edge.getEndpointA().getNumber() > edge.getEndpointB().getNumber()) {
                result.add(new Edge(edge.getEndpointB(), edge.getEndpointA()));
            } else {
                result.add(edge);
            }
        }
        return result;
    }
}