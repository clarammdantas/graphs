

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
    private String SIMPLE_GRAPH_PATH;
    private String WEIGHTED_GRAPH_PATH;

    private GraphLibrary graphLibrary;

    @Before
    public void setup() {
        SIMPLE_GRAPH_PATH = new File("src/main/resources/simple-graph.txt").getAbsolutePath();
        WEIGHTED_GRAPH_PATH = new File("src/main/resources/weighted-graph.txt").getAbsolutePath();

        graphLibrary = new GraphLibrary();
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
