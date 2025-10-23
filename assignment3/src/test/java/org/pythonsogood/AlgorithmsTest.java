package org.pythonsogood;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pythonsogood.algorithms.Kruskal;
import org.pythonsogood.algorithms.Prim;

public class AlgorithmsTest {
	private Graph connectedGraph() {
        List<Vertex> vertices = Arrays.asList(
            new Vertex("A"),
            new Vertex("B"),
            new Vertex("C"),
            new Vertex("D"),
            new Vertex("E")
        );

        List<Edge> edges = Arrays.asList(
            new Edge("A", "B", 2),
            new Edge("A", "C", 3),
            new Edge("B", "C", 5),
            new Edge("B", "D", 6),
            new Edge("C", "D", 7),
            new Edge("D", "E", 9),
            new Edge("C", "E", 4)
        );

        return new Graph(new ArrayList<>(vertices), new ArrayList<>(edges));
    }

    private Graph disconnectedGraph() {
        List<Vertex> vertices = Arrays.asList(
            new Vertex("A"),
            new Vertex("B"),
            new Vertex("C"),
            new Vertex("D"),
            new Vertex("E")
        );

        List<Edge> edges = Arrays.asList(
            new Edge("A", "B", 1),
            new Edge("C", "D", 2),
            new Edge("D", "E", 3)
        );

        return new Graph(new ArrayList<>(vertices), new ArrayList<>(edges));
    }
}
