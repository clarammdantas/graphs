import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import enumeration.Representation;
import exception.GraphLibraryException;
import library.GraphLibrary;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;

import org.junit.Assert;
import org.junit.Before;
import static org.junit.Assert.*;



public class GraphLibraryTest {
	private static final int TEST_SIZE = 10;
	
    private String SIMPLE_GRAPH_PATH;
    private String SIMPLE_GRAPH_PATH2;
    private String WEIGHTED_GRAPH_PATH1;
    private String WEIGHTED_GRAPH_PATH2;
    private String WEIGHTED_GRAPH_PATH3;
    private String WEIGHTED_GRAPH_PATH4;
    private String GRAPH_MORE_THAN_ONE_COMPONENT;

    private Vertex[] V;

    @Before
    public void setup() {
        SIMPLE_GRAPH_PATH = new File("src/main/resources/simple-graph.txt").getAbsolutePath();
        SIMPLE_GRAPH_PATH2 = new File("src/main/resources/simple-graph-2.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH1 = new File("src/main/resources/weighted-graph-1.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH2 = new File("src/main/resources/weighted-graph-2.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH3 = new File("src/main/resources/weighted-graph-3.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH4 = new File("src/main/resources/weighted-graph-4.txt").getAbsolutePath();
        GRAPH_MORE_THAN_ONE_COMPONENT = new File("src/main/resources/graph-with-more-than-one-component.txt").getAbsolutePath();

        V = new Vertex[TEST_SIZE + 1];
        for(int i = 1; i <= TEST_SIZE; i++) {
        	V[i] = new Vertex(i);
        }
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
        Graph<Edge> graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        List<Edge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }

    @Test
    public void BFSShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);

        String expectedResult = "1-0 -\n2-1 1\n3-3 5\n4-3 5\n5-1 1\n";
        assertEquals(expectedResult, GraphLibrary.BFS(graph, new Vertex(1)));

        String expectedResult2 = "1-1 5\n2-1 5\n3-1 5\n4-1 5\n5-0 -\n";
        assertEquals(expectedResult2, GraphLibrary.BFS(graph, new Vertex(5)));
        
        graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
        assertEquals("1-0 -\n", GraphLibrary.BFS(graph, new Vertex(1)));
    }

    @Test
    public void DFSShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);

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
        
        graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
        assertEquals("1-0 -\n", GraphLibrary.BFS(graph, new Vertex(1)));
    }

    @Test
    public void DFSShouldWorkWithUnconnectedGraph() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = GraphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);

        String expectedResult = "1-0 -\n" +
                "2-0 -\n" +
                "3-1 2\n" +
                "4-1 2\n";

        assertEquals(expectedResult, GraphLibrary.DFS(graph, new Vertex(1)));
    }

    @Test
    public void BFSShouldWorkWithUnconnectedGraph() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph = GraphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);

        String expectedResult = "1-0 -\n" +
                "2-0 -\n" +
                "3-1 2\n" +
                "4-1 2\n";

        assertEquals(expectedResult, GraphLibrary.BFS(graph, new Vertex(1)));
    }

    @Test
    public void readWeightedGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        Graph<WeightedEdge> graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
        List<WeightedEdge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }
    

	@Test
	public void getVertexNumber() throws GraphLibraryException, FileNotFoundException {
		Graph<Edge> graphOne = buildGraph();
		int expectedNumberVertex;
		int numberAddedVertex;
		
		expectedNumberVertex = GraphLibrary.getVertexNumber(graphOne);
		numberAddedVertex = 5;
		
		Assert.assertEquals(expectedNumberVertex, numberAddedVertex);

		graphOne = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
		Assert.assertEquals(GraphLibrary.getVertexNumber(graphOne), 1);
	}
	
	@Test
	public void testGetEdgeNumber() throws GraphLibraryException, FileNotFoundException {
		Graph<Edge> graphOne = buildGraph();
		int expectedNumberEdges;
		int numberAddedEdges;
		
		expectedNumberEdges = GraphLibrary.getEdgeNumber(graphOne);
		numberAddedEdges = 4;
		
		Assert.assertEquals(expectedNumberEdges, numberAddedEdges);
		
		graphOne = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
		Assert.assertEquals(GraphLibrary.getEdgeNumber(graphOne), 0);
	}
	
	@Test
	public void testGetMeanEdge() throws GraphLibraryException, FileNotFoundException {
		Graph<Edge> graphOne = buildGraph();
		float expectedMeanEdge;
		float numberMeanEdge;
		float delta = 0;
		
		expectedMeanEdge = GraphLibrary.getMeanEdge(graphOne);
		numberMeanEdge =  1.6f;
		Assert.assertEquals(expectedMeanEdge, numberMeanEdge, delta);
		
		graphOne = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
		Assert.assertEquals(GraphLibrary.getMeanEdge(graphOne), 0, delta);
	}
	
	 @Test
	 public void testConnected() throws GraphLibraryException, FileNotFoundException {
		 Graph<Edge> graph = buildGraph();
	     Assert.assertTrue(GraphLibrary.connected(graph)); 
	     
	     graph.setNumberOfVertices(7);
	     	     
	     Vertex vertex6 = new Vertex(6);
	     Vertex vertex7 = new Vertex(7);
	     
	     Edge edge5 = new Edge(vertex6, vertex7);
			
	     graph.addEdge(edge5);
	     	   
	     Assert.assertFalse(GraphLibrary.connected(graph));
	     
	     graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
	     Assert.assertTrue(GraphLibrary.connected(graph)); 
	}

    @Test
    public void MSTTest() throws FileNotFoundException, GraphLibraryException {
    	Graph<WeightedEdge> graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH2);
    	String tree = GraphLibrary.MST(graph); 
    	
    	assertEquals(tree, 
    			"1-0 -\n" + 
    			"2-1 1\n" + 
    			"3-4 4\n" + 
    			"4-3 5\n" + 
    			"5-2 2\n");
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
    	tree = GraphLibrary.MST(graph); 
    	
    	assertEquals(tree, 
    			"1-0 -\n" + 
    			"2-1 1\n" + 
    			"3-3 5\n" + 
    			"4-3 5\n" + 
    			"5-2 2\n");
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH4);
    	try {
    		tree = GraphLibrary.MST(graph);
    		fail("Exception not thrown");
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Graph is not connected");
    	}
    }
    
    @Test
    public void ShortestPathTest() throws FileNotFoundException, GraphLibraryException {
    	Graph<WeightedEdge> graph;
    	String path;
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
    	
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	assertEquals(path, "1 2 5 3");
   
    	path = GraphLibrary.shortestPath(graph, V[2], V[2]); 
    	assertEquals(path, "2");
    	
    	path = GraphLibrary.shortestPath(graph, V[3], V[1]); 
    	assertEquals(path, "3 5 2 1");
    	
    	path = GraphLibrary.shortestPath(graph, V[4], V[2]); 
    	assertEquals(path, "4 5 2");
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH2);
    	try {
	    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
	    	fail("Exception not thrown");
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Graph has a negative cycle");
    	}
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH3);
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
    	assertEquals(path, "1 2 5 4 3");
    	
    	graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH4);
    	try {
	    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
	    	fail("Exception not thrown");
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Vertex is not reachable");
    	}
    }
    
    @Test
    public void ShortestPathUnWeightedTest() throws FileNotFoundException, GraphLibraryException {
    	Graph<Edge> graph;
    	String path;
    	
    	graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);
    	
    	path = GraphLibrary.shortestPath(graph, V[1], V[3]);
    	assertEquals(path, "1 5 3");

    	path = GraphLibrary.shortestPath(graph, V[2], V[1]);
    	assertEquals(path, "2 1");
    	
    	path = GraphLibrary.shortestPath(graph, V[3], V[4]);
    	assertEquals(path, "3 5 4");
    	
    	path = GraphLibrary.shortestPath(graph, V[5], V[5]);
    	assertEquals(path, "5");
    	
    	
    	graph = GraphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);
    	path = GraphLibrary.shortestPath(graph, V[3], V[4]);
    	assertEquals(path, "3 2 4");
    	
    	try {
	    	path = GraphLibrary.shortestPath(graph, V[1], V[3]); 
	    	fail("Exception not thrown");
    	} catch(Exception e) {
    		assertEquals(e.getMessage(), "Vertex is not reachable");
    	}
    	
    	graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
    	path = GraphLibrary.shortestPath(graph, V[1], V[1]);
    	assertEquals(path, "1");
    }
    
    @Test
    public void NumberOfConnectedComponentsTest() throws FileNotFoundException, GraphLibraryException {
    	Graph<Edge> graph;
    	Graph<WeightedEdge> graphWeighted;
    	
    	graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);
    	assertEquals(GraphLibrary.numberOfConnectedComponents(graph), 1);
    	
    	graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH2);
    	assertEquals(GraphLibrary.numberOfConnectedComponents(graph), 1);
    	
    	graph = GraphLibrary.readGraph(GRAPH_MORE_THAN_ONE_COMPONENT);
    	assertEquals(GraphLibrary.numberOfConnectedComponents(graph), 2);
    	
    	graphWeighted = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH4);
    	assertEquals(GraphLibrary.numberOfConnectedComponents(graphWeighted), 2);
    }

    @Test
    public void representationTest() throws FileNotFoundException, GraphLibraryException {
        Graph<Edge> graph;
        String representationAM;
        String representationAL;
        String AMType = Representation.ADJACENCY_MATRIX.getType();
        String ALType = Representation.ADJACENCY_LIST.getType();
    	
        graph = GraphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        representationAM = "  1 2 3 4 5\n" +
                           "1 0 1 0 0 1\n" + 
                           "2 1 0 0 0 1\n" +
                           "3 0 0 0 0 1\n" +
                           "4 0 0 0 0 1\n" +
                           "5 1 1 1 1 0\n";
        assertEquals(representationAM, GraphLibrary.graphRepresentation(graph, AMType));
        representationAL = "1 - 2 5\n" +
                           "2 - 1 5\n" +
                           "3 - 5\n" +
                           "4 - 5\n" +
                           "5 - 1 2 3 4\n";
        assertEquals(representationAL, GraphLibrary.graphRepresentation(graph,ALType));
    }
    @Test
    public void wheightedRepresentationTest() throws FileNotFoundException, GraphLibraryException { 
        Graph<WeightedEdge> graph;
        String representationAM;
        String representationAL;
        String AMType = Representation.ADJACENCY_MATRIX.getType();
        String ALType = Representation.ADJACENCY_LIST.getType();

        graph = GraphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH1);
        representationAM = "  1 2 3 4 5\n" +
                           "1 0.0 0.1 0.0 0.0 0.5\n" + 
                           "2 0.1 0.0 0.0 0.0 0.3\n" +
                           "3 0.0 0.0 0.0 0.0 0.2\n" +
                           "4 0.0 0.0 0.0 0.0 0.5\n" +
                           "5 0.5 0.3 0.2 0.5 0.0\n";
        assertEquals(representationAM, GraphLibrary.graphRepresentation(graph, AMType));
        representationAL = "1 - 2(0.1) 5(0.5)\n" +
                           "2 - 1(0.1) 5(0.3)\n" +
                           "3 - 5(0.2)\n" +
                           "4 - 5(0.5)\n" +
                           "5 - 2(0.3) 3(0.2) 4(0.5) 1(0.5)\n";
        assertEquals(representationAL, GraphLibrary.graphRepresentation(graph,ALType));
    }
}

