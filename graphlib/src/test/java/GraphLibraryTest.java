import exception.GraphLibraryException;
import library.GraphLibrary;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GraphLibraryTest {
	private static final int TEST_SIZE = 10;
	
    private String SIMPLE_GRAPH_PATH;
    private String WEIGHTED_GRAPH_PATH1;
    private String WEIGHTED_GRAPH_PATH2;
    private String WEIGHTED_GRAPH_PATH3;
    private String WEIGHTED_GRAPH_PATH4;

    private GraphLibrary graphLibrary;
    
    private Vertex[] V;

    @Before
    public void setup() {
        SIMPLE_GRAPH_PATH = new File("src/main/resources/simple-graph.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH1 = new File("src/main/resources/weighted-graph-1.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH2 = new File("src/main/resources/weighted-graph-2.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH3 = new File("src/main/resources/weighted-graph-3.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH4 = new File("src/main/resources/weighted-graph-4.txt").getAbsolutePath();

        graphLibrary = new GraphLibrary();
        
        V = new Vertex[TEST_SIZE + 1];
        for(int i = 1; i <= TEST_SIZE; i++) {
        	V[i] = new Vertex(i);
        }
    }

    @Test
    public void readGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        List<Edge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }

    @Test
    public void DFSShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);

        String expectedResult = "1-0 -\n2-1 1\n3-3 5\n4-3 5\n5-2 2\n";
        assertEquals(expectedResult, GraphLibrary.DFS(graph, new Vertex(1)));

        String expectedResult2 = "1-1 2\n2-0 -\n3-3 5\n4-3 5\n5-2 1\n";
        assertEquals(expectedResult2, GraphLibrary.DFS(graph, new Vertex(2)));
    }

    @Test
    public void readWeightedGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
        List<WeightedEdge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }
    
    @Test
    public void MSTShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
    	Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH2);
    	String tree = GraphLibrary.MST(graph); 
    	
    	assertEquals(tree, 
    			"1-0 -\n" + 
    			"2-1 1\n" + 
    			"3-4 4\n" + 
    			"4-3 5\n" + 
    			"5-2 2\n");
    	
    	graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
    	tree = GraphLibrary.MST(graph); 
    	
    	assertEquals(tree, 
    			"1-0 -\n" + 
    			"2-1 1\n" + 
    			"3-3 5\n" + 
    			"4-3 5\n" + 
    			"5-2 2\n");
    }
    
    @Test
    public void ShortestPathWeighted() throws FileNotFoundException, GraphLibraryException {
    	Graph<WeightedEdge> graph;
    	String path;
    	
    	graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
    	
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	assertEquals(path, "1 2 5 3");
   
    	path = GraphLibrary.shortestPath(graph, V[2], V[2]); 
    	assertEquals(path, "2");
    	
    	path = GraphLibrary.shortestPath(graph, V[3], V[1]); 
    	assertEquals(path, "3 5 2 1");
    	
    	path = GraphLibrary.shortestPath(graph, V[4], V[2]); 
    	assertEquals(path, "4 5 2");
    	
    	graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH2);
    	try {
	    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Graph has a negative cycle");
    	}
    	
    	graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH3);
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	assertEquals(path, "1 2 5 4 3");
    	
    	graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH4);
    	try {
	    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Vertex is not reachable");
    	}
    }
    
    @Test
    public void ShortestPathUnWeighted() throws FileNotFoundException, GraphLibraryException {
    	Graph<Edge> graph;
    	String path;
    	
    	graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
    	
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]);
    	assertEquals(path, "1 5 3");

    	path = GraphLibrary.shortestPath(graph, V[2], V[1]);
    	assertEquals(path, "2 1");
    	
    	path = GraphLibrary.shortestPath(graph, V[3], V[4]);
    	assertEquals(path, "3 5 4");
    	
    	path = GraphLibrary.shortestPath(graph, V[5], V[5]);
    	assertEquals(path, "5");
    }
}
