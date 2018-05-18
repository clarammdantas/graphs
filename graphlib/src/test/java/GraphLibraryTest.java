import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    private String GRAPH_MORE_THAN_ONE_COMPONENT;

    private GraphLibrary graphLibrary;
    
    private Vertex[] V;

    @Before
    public void setup() {
        SIMPLE_GRAPH_PATH = new File("src/main/resources/simple-graph.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH1 = new File("src/main/resources/weighted-graph-1.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH2 = new File("src/main/resources/weighted-graph-2.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH3 = new File("src/main/resources/weighted-graph-3.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH4 = new File("src/main/resources/weighted-graph-4.txt").getAbsolutePath();
        GRAPH_MORE_THAN_ONE_COMPONENT = new File("src/main/resources/graph-with-more-than-one-component.txt").getAbsolutePath();

        graphLibrary = new GraphLibrary();
        
        V = new Vertex[TEST_SIZE + 1];
        for(int i = 1; i <= TEST_SIZE; i++) {
        	V[i] = new Vertex(i);
        }
    }

    private GraphLibrary graphLibrary;
	
	@Before
	public void setUp() {
		this.graphLibrary = new GraphLibrary();
	}
	
	private Graph<Edge> buildGraph() throws GraphLibraryException {
		
		Graph<Edge> graph = new Graph<Edge>(5);
		
		Vertex vertex1 = new Vertex(1);
		Vertex vertex2 = new Vertex(2);
		Vertex vertex3 = new Vertex(3);
		Vertex vertex4 = new Vertex(4);
		Vertex vertex5 = new Vertex(5);
		
		
		Edge edge1 = new Edge(vertex1, vertex2);
		Edge edge2 = new Edge(vertex2, vertex3);
		Edge edge3 = new Edge(vertex3, vertex4);
		Edge edge4 = new Edge(vertex4, vertex5);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);

		return graph;
	}
    
    @Test
    public void readGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        List<Edge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }

    @Test
    public void BFSShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);

        String expectedResult = "1-0 -\n2-1 1\n3-3 5\n4-3 5\n5-1 1\n";
        assertEquals(expectedResult, graphLibrary.BFS(graph, new Vertex(1)));

        String expectedResult2 = "1-1 5\n2-1 5\n3-1 5\n4-1 5\n5-0 -\n";
        assertEquals(expectedResult2, graphLibrary.BFS(graph, new Vertex(5)));
    }

    @Test
    public void DFSShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);

        String expectedResult = "1-0 -\n" +
                "2-1 1\n" +
                "3-3 5\n" +
                "4-3 5\n" +
                "5-2 2\n";
        assertEquals(expectedResult, GraphLibrary.DFS(graph, new Vertex(1)));

        String expectedResult2 = "1-1 2\n" +
                "2-0 -\n" +
                "3-3 5\n" +
                "4-3 5\n" +
                "5-2 1\n";
        assertEquals(expectedResult2, GraphLibrary.DFS(graph, new Vertex(2)));
    }

    @Test
    public void DFSShouldWorkWithUnconnectedGraph() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);

        String expectedResult = "1-0 -\n" +
                "2-0 -\n" +
                "3-1 2\n" +
                "4-1 2\n";

        assertEquals(expectedResult, graphLibrary.DFS(graph, new Vertex(1)));
    }

    @Test
    public void BFSShouldWorkWithUnconnectedGraph() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = graphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);

        String expectedResult = "1-0 -\n" +
                "2-0 -\n" +
                "3-1 2\n" +
                "4-1 2\n";

        assertEquals(expectedResult, graphLibrary.BFS(graph, new Vertex(1)));
    }

    @Test
    public void readWeightedGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
        List<WeightedEdge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }
    

	@Test
	public void getVertexNumber() throws GraphLibraryException {
		Graph<Edge> graphOne = buildGraph();
		int expectedNumberVertex;
		int numberAddedVertex;
		
		expectedNumberVertex = this.graphLibrary.getVertexNumber(graphOne);
		numberAddedVertex = 5;
		
		Assert.assertEquals(expectedNumberVertex, numberAddedVertex);
	}
	
	@Test
	public void testGetEdgeNumber() throws GraphLibraryException {
		Graph<Edge> graphOne = buildGraph();
		int expectedNumberEdges;
		int numberAddedEdges;
		
		expectedNumberEdges = this.graphLibrary.getEdgeNumber(graphOne);
		numberAddedEdges = 4;
		
		Assert.assertEquals(expectedNumberEdges, numberAddedEdges);
	}
	
	@Test
	public void testGetMeanEdge() throws GraphLibraryException {
		Graph<Edge> graphOne = buildGraph();
		float expectedMeanEdge;
		float numberMeanEdge;
		float delta = 0;
		
		expectedMeanEdge = this.graphLibrary.getMeanEdge(graphOne);
		numberMeanEdge =  1.6f;
		Assert.assertEquals(expectedMeanEdge, numberMeanEdge, delta);
	}
	
	 @Test
	 public void testConnected() throws GraphLibraryException {
		 Graph<Edge> graph = buildGraph();
	     Assert.assertTrue(graphLibrary.connected(graph)); 
	     
	     graph.setNumberOfVertices(7);
	     	     
	     Vertex vertex6 = new Vertex(6);
	     Vertex vertex7 = new Vertex(7);
	     
	     Edge edge5 = new Edge(vertex6, vertex7);
			
	     graph.addEdge(edge5);
	     	   
	     Assert.assertFalse(graphLibrary.connected(graph));
	}

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

