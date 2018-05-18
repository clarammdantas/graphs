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

public class GraphLibraryTest {
    private static final String SIMPLE_GRAPH_PATH = "/home/gustavo/Documents/workspace/graphs/graphlib/src/main/resources/simple-graph.txt";
    private static final String WEIGHTED_GRAPH_PATH = "/home/gustavo/Documents/workspace/graphs/graphlib/src/main/resources/weighted-graph.txt";

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
    private Graph<Edge> buildGraph2() throws GraphLibraryException {
        
        Graph<Edge> graph = new Graph<Edge>(7);
       
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);
        Vertex vertex7 = new Vertex(7);
 
       
       
        Edge edge1 = new Edge(vertex1, vertex2);
        Edge edge2 = new Edge(vertex5, vertex1);
        Edge edge3 = new Edge(vertex2, vertex5);
        Edge edge4 = new Edge(vertex5, vertex3);
        Edge edge5 = new Edge(vertex5, vertex4);
        Edge edge6 = new Edge(vertex6, vertex7);
       
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        graph.addEdge(edge6);
 
        return graph;
    }
    
    @Test
    public void readGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        List<Edge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }

    @Test
    public void readWeightedGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH);
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
	     Graph<Edge> graph2 = buildGraph2();
	     Assert.assertTrue(graphLibrary.connected(graph));      
	     Assert.assertFalse(graphLibrary.connected(graph2));
	}

}