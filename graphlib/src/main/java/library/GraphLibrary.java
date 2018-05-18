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

import java.util.*;


public class GraphLibrary {
	
	private static final double INF_WEIGHT = 1e10;

    /**
     * Reads an unweighted graph from a file.
     * @param filePath
     * @return Graph
     * @throws GraphLibraryException
     * @throws FileNotFoundException
     */
    public static Graph<Edge> readGraph(String filePath) throws GraphLibraryException, FileNotFoundException {
        GraphReader graphReader = new GraphReader(filePath);

        int numberOfVertices = graphReader.nextInt();
        Graph<Edge> graph = new Graph<Edge>(numberOfVertices);

        while (graphReader.hasNextLine()) {
            Edge edge = graphReader.nextEdge();
            graph.addEdge(edge);
        }

        return graph;
    }

    /**
     * Reads a weighted graph from a file.
     * @param filePath
     * @return Graph
     * @throws GraphLibraryException
     * @throws FileNotFoundException
     */
    public static Graph<WeightedEdge> readWeightedGraph(String filePath) throws GraphLibraryException, FileNotFoundException {
        GraphReader graphReader = new GraphReader(filePath);

        int numberOfVertices = graphReader.nextInt();
        Graph<WeightedEdge> graph = new Graph<WeightedEdge>(numberOfVertices);

        while (graphReader.hasNextLine()) {
            WeightedEdge edge = graphReader.nextWeightedEdge();
            graph.addEdge(edge);
        }

        return graph;
    }

    
    public static int getVertexNumber(Graph<Edge> graph) {
    	return graph.getNumberOfVertices() ;
    }
    
    public static int getEdgeNumber(Graph<Edge> graph) {
    	return graph.getEdges().size();
    }
    
    public static float getMeanEdge(Graph<Edge> graph) {
    	return (float) 2 * getEdgeNumber(graph) / getVertexNumber(graph);
    }
    
    public static boolean connected(Graph<Edge> graph) {
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
 
    private static boolean isConect(List<Edge> edges, Vertex vertex1, Vertex vertex2) {
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
   
    private static List<Edge> orderEdges(List<Edge> edges) {
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



    /**
     * Returns the string with the representation of the graph in the choosen format
     * @param graph graph
     * @param type The type of the graph representation(Adjacency List(AL) or Adjacenct Matrix(AM))
     * @return String Representação do gráfico no tipo escolhido
     */
    
    public static String graphRepresentation(Graph graph, String type) {
        return graph.representation(type);
    }
    

    
    /**
     * Does a BFS on a graph g starting at vertex v. The graph can have more than one component.
     * @param g graph
     * @param v origin vertex
     * @return
     */
    public static String BFS(Graph<Edge> g, Vertex v) {
        final int LEVEL_AND_PARENT = 2;
        //each element is an array, positioned at the index correspondent to the vertex number,
        //that stores, the vertex level and the parent of the current vertex, in that order.
        Object[][] tree = new Object[g.getNumberOfVertices() + 1][LEVEL_AND_PARENT];
        Set<Vertex> vertices = g.getVertices();
        Map<Integer, Boolean> visited = setVisited(vertices);
        BFSAux(g, v, tree, visited);

        for (Vertex vertex : vertices) {
            if (!visited.get(vertex.getNumber())) {
                BFSAux(g, vertex, tree, visited);
            }
        }

        return getTree(tree);
    }

    /**
     * BFS that runs in a graph component.
     * @param g Graph
     * @param v Vertex
     * @param tree Representation of the tree generated by the BFS when it runs on a graph.
     *             The tree is an Array where every element index corresponds to the vertex
     *             number. The elements are arrays where the first value indicates the vertex's
     *             level and the second value indicates the parent of that vertex.
     * @param visited Map that stores the vertices already visited.
     */
    private static void BFSAux(Graph<Edge> g, Vertex v,
                                Object[][] tree, Map<Integer, Boolean> visited) {
        int LEVEL = 0;

        Set<Vertex> vertices = g.getVertices();

        LinkedList<Vertex> verticesQueue = new LinkedList();
        visited.put(v.getNumber(), true);
        verticesQueue.add(v);

        while (verticesQueue.size() != 0) {
            Vertex current_parent = verticesQueue.poll();

            if (LEVEL == 0) {
                tree[current_parent.getNumber()] = new Object[]{LEVEL, "-"};
            }

            List<Vertex> neighboursVertices = g.getNeighbours(current_parent);
            for (Vertex vertex : neighboursVertices) {
                if (!visited.get(vertex.getNumber())) {
                    visited.put(vertex.getNumber(), true);
                    tree[vertex.getNumber()] = new Object[]{LEVEL + 1, current_parent.getNumber()};

                    verticesQueue.add(vertex);
                }
            }

            LEVEL += 1;
        }
    }

    /**
     *
     * @param g Graph
     * @param v Vertex
     * @return The tree generated when the DFS runs in a graph.
     */
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

    /**
     *
     * @param g Graph
     * @param visited Map that stores the vertices already visited.
     * @param vertex Vertex
     * @param level Current tree level
     * @param tree Representation of the tree generated by the BFS when it runs on a graph.
     *             The tree is an Array where every element index corresponds to the vertex
     *             number. The elements are arrays where the first value indicates the vertex's
     *             level and the second value indicates the parent of that vertex.
     */
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

    /**
     * Initializes the visited map.
     * @param vertices Graph's Set of its vertices
     * @return
     */
    private static Map<Integer, Boolean> setVisited(Set<Vertex> vertices) {
        Map<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        for (Vertex v : vertices) {
            visited.put(v.getNumber(), false);
        }

        return visited;
    }

    /**
     *
     * @param tree Representation of the tree generated by the BFS when it runs on a graph.
     *             The tree is an Array where every element index corresponds to the vertex
     *             number. The elements are arrays where the first value indicates the vertex's
     *             level and the second value indicates the parent of that vertex.
     * @return String that corresponds to the tree
     */
    private static String getTree(Object[][] tree) {
        String toStringTree = "";

        for (int i = 0; i < tree.length; i++) {
            if (tree[i][0] != null && tree[i][1] != null) {
                toStringTree += i + "-" + tree[i][0] + " " + tree[i][1] + "\n";
            }
        }

        return toStringTree;
    }

    /**
     * Gives the minimum spanning tree of a given graph.
     * @param g Graph
     * @return The tree generated by the DFS when the minimum spanning tree of a graph
     *         is given.
     * @throws GraphLibraryException
     */
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
    	
    	if(mst.getNumberOfEdges() != g.getNumberOfVertices() - 1) {
    		throw new GraphLibraryException("Graph is not connected");
    	}

    	return DFS(mst, new Vertex(1));
    }

    /**
     * Calculates the shortest path between a source vertex and a target vertex in a weighted graph.
     * @param g Graph
     * @param source Origin vertex
     * @param target Target vertex
     * @return The shortest path between the source vertex and the target vertex.
     * @throws GraphLibraryException
     */
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
    		pathStr += " " + Integer.toString(path.pop().getNumber());
    	}
    	
    	return pathStr;
    }
    
    private static void relax(Vertex A, Vertex B, double weight, Vertex parent[], double distance[]) {
    	if(distance[B.getNumber()] > distance[A.getNumber()] + weight) {
    		distance[B.getNumber()] = distance[A.getNumber()] + weight;
    		parent[B.getNumber()] = A;
    	}
	}

    /**
     * Calculates the shortest path between a source vertex and a target vertex in any graph.
     * @param g Graph
     * @param source Origin vertex
     * @param target Target vertex
     * @return The shortest path between the source vertex and the target vertex
     * @throws GraphLibraryException
     */
	public static <T extends Edge> String shortestPath(Graph<T> g, Vertex source, Vertex target) throws GraphLibraryException {
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

