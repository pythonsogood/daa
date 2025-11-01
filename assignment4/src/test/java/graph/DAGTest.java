package graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graph.instrumentation.Runner;
import graph.instrumentation.TimedOutput;
import graph.objects.Edge;
import graph.objects.Graph;
import graph.objects.Path;

public class DAGTest {
	private Runner runner;

	@BeforeEach
	public void setup() {
		this.runner = new Runner();
	}

	@Test
	public void testShortestPathSmallDAG() {
		// 0 -> 1 (2), 1 -> 2 (1), 0 -> 2 (5)
		List<Edge> edges = Arrays.asList(
			new Edge(0, 1, 2),
			new Edge(1, 2, 1),
			new Edge(0, 2, 5)
		);

		Graph graph = new Graph(edges);

		TimedOutput<Path> shortestPath = this.runner.shortestPath(graph, 0);
		List<List<Integer>> constructedPaths = shortestPath.output.construct();

		assertEquals(3, shortestPath.output.distances[2], "Shortest distance to node 2 must be 3");
		assertEquals(Arrays.asList(0, 1, 2), constructedPaths.get(2), "Shortest path to node 2 must be 0 -> 1 -> 2");
	}

	@Test
	public void testLongestPathSmallDAG() {
		// 0 -> 1 (2), 1 -> 2 (10), 0 -> 2 (5)
		List<Edge> edges = Arrays.asList(
			new Edge(0, 1, 2),
			new Edge(1, 2, 10),
			new Edge(0, 2, 5)
		);

		Graph graph = new Graph(edges);

		TimedOutput<Path> longestPath = this.runner.longestPath(graph, 0);
		List<List<Integer>> constructedPaths = longestPath.output.construct();

		assertEquals(12, longestPath.output.distances[2], "Longest distance to node 2 must be 12");
		assertEquals(Arrays.asList(0, 1, 2), constructedPaths.get(2), "Longest path to node 2 must be 0 -> 1 -> 2");
	}

	@Test
	public void testShortestPathUnreachable() {
		// 4 isolated nodes
		Graph graph = new Graph(4, new ArrayList<>());

		TimedOutput<Path> shortestPath = this.runner.shortestPath(graph, 0);
		List<List<Integer>> constructedPaths = shortestPath.output.construct();

		assertEquals(-1, shortestPath.output.distances[1], "Node 1 must be unreachable (-1)");
		assertNull(constructedPaths.get(1), "No path should be constructed to unreachable node");
	}
}
