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
    
    public static String DFS(Graph<Edge> g, Vertex v) {
        final int LEVEL_AND_PARENT = 2;
        Set<Vertex> vertices = g.getVertices();
        Map<Integer, Boolean> visited = setVisited(vertices);

        //each element is an array, positioned at the index correspondent to the vertex number,
        //that stores, the vertex level and the parent of the current vertex, in that order.
        Object[][] tree = new Object[g.getNumberOfVertices() + 1][LEVEL_AND_PARENT];
        List<Object> firstProcessedVertex = new ArrayList<Object>();

        tree[v.getNumber()] = new Object[]{0, "-"}; // it doesn't have a parent because this vertex is a root.
        DFSAux(g, visited, v, 0, tree);

        for (Vertex vertex : vertices) {
            if (!visited.get(vertex.getNumber())) {
                firstProcessedVertex.add(vertex);
                tree[vertex.getNumber()] = new Object[]{0, "-"};

                DFSAux(g, visited, vertex, 0, tree);
            }
        }

    	return getTree(tree);
    }

    private static void DFSAux(Graph<Edge> g, Map<Integer, Boolean> visited,
                               Vertex vertex, Integer level, Object[][] tree) {

        visited.put(vertex.getNumber(), true);

        List<Vertex> neighbours = g.getNeighbours(vertex);
        for (Vertex neighbourVertex : neighbours) {
            if (!visited.get(neighbourVertex.getNumber())) {
                Object[] treeRow = new Object[]{level + 1, vertex.getNumber()};

                tree[neighbourVertex.getNumber()] = treeRow;
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

    private static String getTree(Object[][] tree) {
        String toStringTree = "";

        for (int i = 0; i < tree.length; i++) {
            if (tree[i][0] != null && tree[i][1] != null) {
                toStringTree += i + "-" + tree[i][0] + " " + tree[i][1] + "\n";
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

    	return DFS(mst, new Vertex(1));
    }
}
