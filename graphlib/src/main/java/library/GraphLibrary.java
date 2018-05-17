package library;

import com.sun.org.apache.xpath.internal.operations.Bool;
import exception.GraphLibraryException;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import util.GraphReader;

import java.io.FileNotFoundException;
import java.util.*;

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
    
    public String DFS(Graph<Edge> g, Vertex v) {
        Set<Vertex> vertices = g.getVertices();
        Map<Integer, Boolean> visited = setVisited(vertices);
        List<List<Object>> tree = new ArrayList<List<Object>>();
        List<Object> firstProcessedVertex = new ArrayList<Object>();

        firstProcessedVertex.add(v);
        tree.add(firstProcessedVertex);
        DFSAux(g, visited, v, 0, tree);

        for (Vertex vertex : vertices) {
            if (!visited.get(vertex.getNumber())) {
                firstProcessedVertex.add(vertex);
                tree.add(firstProcessedVertex);

                DFSAux(g, visited, vertex, 0, tree);
            }
        }

    	return getTree(tree);
    }

    private void DFSAux(Graph<Edge> g, Map<Integer, Boolean> visited,
                               Vertex vertex, Integer level, List<List<Object>> tree) {

        visited.put(vertex.getNumber(), true);

        List<Vertex> neighbours = g.getNeighbours(vertex);
        for (Vertex neighbourVertex : neighbours) {
            if (!visited.get(neighbourVertex.getNumber())) {
                List<Object> treeRow = new ArrayList<Object>();
                treeRow.add(neighbourVertex);
                treeRow.add(level + 1);
                treeRow.add(vertex);

                tree.add(treeRow);
                DFSAux(g, visited, neighbourVertex, level + 1, tree);
            }
        }
    }

    private static Map<Integer, Boolean> setVisited(Set<Vertex> vertices) {
        Map<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        for (Vertex v : vertices) {
            visited.put(v.getNumber(), false);
        }

        return visited;
    }

    private static String getTree(List<List<Object>> tree) {
        String toStringTree = "";

        for (int i = 0; i < tree.size(); i++) {
            if (tree.get(i).size() <= 1) {
                toStringTree += ((Vertex) tree.get(i).get(0)).getNumber() + "-0 -" + "\n";
            } else {
                toStringTree += ((Vertex)tree.get(i).get(0)).getNumber() + "-" +
                        ((Integer) tree.get(i).get(1)).toString() +
                        " " + ((Vertex)tree.get(i).get(2)).getNumber() + "\n";
            }
        }

        return toStringTree;
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

    	return "oi";
    	//return DFS(mst, new Vertex(1));
    }
}
