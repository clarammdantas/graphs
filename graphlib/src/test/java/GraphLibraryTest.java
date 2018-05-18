

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import exception.GraphLibraryException;
import library.GraphLibrary;
import model.Edge;
import model.Graph;
import model.WeightedEdge;

public class GraphLibraryTest {
    private static final String SIMPLE_GRAPH_RELATIVE_PATH = "graphlib/src/main/resources/simple-graph.txt";
    private static final String SIMPLE_GRAPH_PATH = new File(SIMPLE_GRAPH_RELATIVE_PATH).getAbsolutePath();

    private static final String WEIGHTED_GRAPH_RELATIVE_PATH = "graphlib/src/main/resources/weighted-graph.txt";
    private static final String WEIGHTED_GRAPH_PATH = new File(WEIGHTED_GRAPH_RELATIVE_PATH).getAbsolutePath();

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
    public void checkPrint() throws FileNotFoundException, GraphLibraryException {
        GraphLibrary graphLibrary = new GraphLibrary();
        Graph<WeightedEdge> graph = graphLibrary.readWeightedGraph(WEIGHTED_GRAPH_PATH);
        assertEquals("a", graphLibrary.graphRepresentation(graph, "A"));
        System.out.println(graphLibrary.graphRepresentation(graph, "AM"));
        System.out.println(graphLibrary.graphRepresentation(graph, "AL"));
    }
}
