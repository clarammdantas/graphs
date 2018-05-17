import exception.GraphLibraryException;
import library.GraphLibrary;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.WeightedEdge;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GraphLibraryTest {
    private static final String SIMPLE_GRAPH_RELATIVE_PATH = "src/main/resources/simple-graph.txt";
    private static final String SIMPLE_GRAPH_PATH = new File(SIMPLE_GRAPH_RELATIVE_PATH).getAbsolutePath();

    private static final String WEIGHTED_GRAPH_RELATIVE_PATH = "src/main/resources/weighted-graph.txt";
    private static final String WEIGHTED_GRAPH_PATH = new File(WEIGHTED_GRAPH_RELATIVE_PATH).getAbsolutePath();

    @Test
    public void readGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        List<Edge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }

    @Test
    public void getNeighboursShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<Edge> graph = graphLibrary.readGraph(SIMPLE_GRAPH_PATH);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);

        Edge edge1 = new Edge(vertex1, vertex2);
        Edge edge2 = new Edge(vertex1, vertex5);
        List<Edge> edgesFounded = graph.getNeighbours(vertex1);

        List<Edge> edges = new ArrayList<Edge>();
        edges.add(edge1);
        edges.add(edge2);

        assertEquals(edges, edgesFounded);
    }

    @Test
    public void readWeightedGraphShouldWorkProperly() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH);
        List<WeightedEdge> edges = graph.getEdges();

        assertEquals(5, edges.size());
    }
}
