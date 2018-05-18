package util;

import model.Edge;
import model.Vertex;
import model.WeightedEdge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class GraphReader {
    private Scanner scanner;

    public GraphReader(String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
        this.scanner.useLocale(Locale.ENGLISH);
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public int nextInt() {
        return scanner.nextInt();
    }

    public Edge nextEdge() {
        Vertex endpointA = new Vertex(nextInt());
        Vertex endpointB = new Vertex(nextInt());
        return new Edge(endpointA, endpointB);
    }

    public WeightedEdge nextWeightedEdge() {
        Edge edge = nextEdge();
        double weight = scanner.nextDouble();
        return new WeightedEdge(edge.getEndpointA(), edge.getEndpointB(), weight);
    }
}
