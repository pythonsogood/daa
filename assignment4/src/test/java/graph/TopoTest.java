package graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graph.instrumentation.Runner;
import graph.instrumentation.TimedOutput;
import graph.objects.Edge;
import graph.objects.Graph;

public class TopoTest {
	private Runner runner;

	@BeforeEach
	public void setup() {
		this.runner = new Runner();
	}

	@Test
	public void testSimpleChain() {
		// 0 -> 1 -> 2
		List<Edge> edges = Arrays.asList(
			new Edge(0, 1, 1),
			new Edge(1, 2, 1)
		);

		Graph graph = new Graph(edges);

		TimedOutput<List<Integer>> topo = this.runner.topo(graph);

		assertEquals(3, topo.output.size());
		assertEquals(0, topo.output.get(0));
		assertEquals(1, topo.output.get(1));
		assertEquals(2, topo.output.get(2));
	}

	@Test
	public void testDisconnectedGraphContainsAllNodes() {
		// 4 isolated nodes
		Graph graph = new Graph(4, new ArrayList<>());

		TimedOutput<List<Integer>> topo = this.runner.topo(graph);

		assertEquals(4, topo.output.size());
		assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3)), new HashSet<>(topo.output));
	}
}
