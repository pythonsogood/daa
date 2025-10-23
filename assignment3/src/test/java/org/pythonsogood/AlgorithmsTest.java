package org.pythonsogood;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.graph.Vertex;
import org.pythonsogood.models.EdgeJson;
import org.pythonsogood.models.OutputAlgorithmJson;

public class AlgorithmsTest {
	private Runner runner;

	private Graph connectedGraph() {
		List<Vertex> vertices = Arrays.asList(
			new Vertex("A"),
			new Vertex("B"),
			new Vertex("C"),
			new Vertex("D")
		);

		List<Edge> edges = Arrays.asList(
			new Edge("A", "B", 1),
			new Edge("A", "C", 3),
			new Edge("B", "C", 1),
			new Edge("B", "D", 4),
			new Edge("C", "D", 2)
		);

		return new Graph(vertices, edges);
	}

	private Graph disconnectedGraph() {
		List<Vertex> vertices = Arrays.asList(
			new Vertex("A"),
			new Vertex("B"),
			new Vertex("C"),
			new Vertex("D")
		);

		List<Edge> edges = Arrays.asList(
			new Edge("A", "B", 2),
			new Edge("C", "D", 3)
		);

		return new Graph(vertices, edges);
	}

	private void dfs(List<Edge> edges, Vertex current, List<Vertex> visited) {
		if (visited.contains(current)) {
			return;
		}

		visited.add(current);

		for (Edge e : edges) {
			if (current.equals(e.from)) {
				this.dfs(edges, new Vertex(e.to), visited);
			} else if (current.equals(e.to)) {
				this.dfs(edges, new Vertex(e.from), visited);
			}
		}
	}

	private boolean isConnected(Graph graph, List<Edge> edges) {
		if (edges.isEmpty()) {
			return false;
		}

		List<Vertex> visited = new ArrayList<>();
		this.dfs(edges, new Vertex(edges.get(0).from), visited);

		return visited.size() == graph.vertices.size();
	}

	private boolean isAcyclic(Graph graph, List<Edge> edges) {
		return edges.size() == graph.vertices.size() - 1 && this.isConnected(graph, edges);
	}

	@BeforeEach
	public void setup() {
		this.runner = new Runner();
	}

	@Test
	public void testMSTCosts() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);
		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		int primCost = EdgeJson.getCost(primResult.mst_edges);
		int kruskalCost = EdgeJson.getCost(kruskalResult.mst_edges);

		assertEquals(primCost, kruskalCost, "Prim and Kruskal should have the same cost");
	}

	@Test
	public void testPrimEdges() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);

		assertEquals(primResult.mst_edges.size(), graph.vertices.size() - 1, "Prim MST should have V-1 edges");
	}

	@Test
	public void testKruskalEdges() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		assertEquals(kruskalResult.mst_edges.size(), graph.vertices.size() - 1, "Kruskal MST should have V-1 edges");
	}

	@Test
	public void testPrimConnected() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);

		List<Edge> mst = new ArrayList<>();

		for (EdgeJson e : primResult.mst_edges) {
			mst.add(new Edge(e));
		}

		assertTrue(this.isConnected(graph, mst), "Prim MST should connect all vertices");
	}

	@Test
	public void testPrimAcyclic() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);

		List<Edge> mst = new ArrayList<>();

		for (EdgeJson e : primResult.mst_edges) {
			mst.add(new Edge(e));
		}

		assertTrue(this.isAcyclic(graph, mst), "Prim MST should connect all vertices");
	}

	@Test
	public void testKruskalConnected() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		List<Edge> mst = new ArrayList<>();

		for (EdgeJson e : kruskalResult.mst_edges) {
			mst.add(new Edge(e));
		}

		assertTrue(this.isConnected(graph, mst), "Kruskal MST should connect all vertices");
	}

	@Test
	public void testKruskalAcyclic() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		List<Edge> mst = new ArrayList<>();

		for (EdgeJson e : kruskalResult.mst_edges) {
			mst.add(new Edge(e));
		}

		assertTrue(this.isAcyclic(graph, mst), "Kruskal MST should connect all vertices");
	}

	@Test
	public void testDisconnectedPrim() {
		Graph graph = this.disconnectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);

		assertTrue(primResult.mst_edges.size() < graph.vertices.size() - 1, "Prim should not produce MST for disconnected graph");
	}

	@Test
	public void testDisconnectedKruskal() {
		Graph graph = this.disconnectedGraph();

		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		assertTrue(kruskalResult.mst_edges.size() < graph.vertices.size() - 1, "Prim should not produce MST for disconnected graph");
	}

	@Test
	public void testExecutionTimePrim() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson primResult = this.runner.runAlgorithm(this.runner.getPrim(), graph);

		assertTrue(primResult.execution_time_ms > 0, "Prim execution time should be non-negative");
	}

	@Test
	public void testExecutionTimeKruskal() {
		Graph graph = this.connectedGraph();

		OutputAlgorithmJson kruskalResult = this.runner.runAlgorithm(this.runner.getKruskal(), graph);

		assertTrue(kruskalResult.execution_time_ms > 0, "Kruskal execution time should be non-negative");
	}
}
