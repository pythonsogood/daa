package graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graph.instrumentation.Runner;
import graph.instrumentation.TimedOutput;
import graph.objects.Edge;
import graph.objects.Graph;

public class SCCTest {
	private Runner runner;

	@BeforeEach
	public void setup() {
		this.runner = new Runner();
	}

	@Test
	public void testSingleCycle() {
		// 0 -> 1 -> 2 -> 0
		List<Edge> edges = Arrays.asList(
			new Edge(0, 1, 1),
			new Edge(1, 2, 1),
			new Edge(2, 0, 1)
		);

		Graph graph = new Graph(edges);

		TimedOutput<List<List<Integer>>> sccs = this.runner.scc(graph);

		assertEquals(2, sccs.output.size(), "2 SCC expected for full 3-node cycle");

		List<Integer> scc = sccs.output.get(0);
		assertEquals(Arrays.asList(0, 1, 2), scc, "SCC must contain 0,1,2 nodes");
	}

	@Test
	public void testSingleTwoCyclesEdgeBetween() {
		// Cycle 1: 0 <-> 1, Cycle 2: 2 <-> 3, edge 1 -> 2
		List<Edge> edges = Arrays.asList(
			new Edge(0, 1, 1),
			new Edge(1, 0, 1),
			new Edge(2, 3, 1),
			new Edge(3, 2, 1),
			new Edge(1, 2, 1)
		);

		Graph graph = new Graph(edges);

		TimedOutput<List<List<Integer>>> sccs = this.runner.scc(graph);

		assertEquals(4, sccs.output.size(), "4 SCC expected (2 cycles)");

		List<Integer> scc0 = sccs.output.get(0);
		List<Integer> scc1 = sccs.output.get(1);

		assertEquals(Arrays.asList(0, 1), scc0, "SCC0 must be 0,1");
		assertEquals(Arrays.asList(2, 3), scc1, "SCC1 must be 2,3");
	}
}
