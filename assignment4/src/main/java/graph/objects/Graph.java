package graph.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.serialization.InputEdge;
import graph.serialization.InputJson;

public class Graph {
	public final int vertices;
	public final List<Edge> edges;

	public Graph(int vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public Graph(List<Edge> edges) {
		this.edges = edges;

		int vertices = 0;
		for (Edge edge : edges) {
			vertices = Math.max(Math.max(edge.from, vertices), edge.to) + 1;
		}

		this.vertices = vertices;
	}

	public Graph(InputJson inputJson) {
		List<Edge> edges = new ArrayList<>();

		for (InputEdge edgeJson : inputJson.edges) {
			edges.add(new Edge(edgeJson));

			if (!inputJson.directed) {
				edges.add(new Edge(edgeJson.v, edgeJson.u, edgeJson.w));
			}
		}

		this.vertices = inputJson.n;
		this.edges = edges;
	}

	public Graph transposed() {
		List<Edge> edges = new ArrayList<>();

		for (Edge edge : this.edges) {
			edges.add(edge.reversed());
		}

		return new Graph(this.vertices, edges);
	}

	public Integer[][] adjacencyMatrix() {
		Integer[][] adj = new Integer[this.vertices][this.vertices];

		for (Edge edge : this.edges) {
			adj[edge.from][edge.to] = edge.weight;
		}

		return adj;
	}

	public Map<Integer, List<Integer>> adjacencyList() {
		Map<Integer, List<Integer>> adj = new HashMap<>();

		for (Edge edge : this.edges) {
			adj.computeIfAbsent(edge.from, x -> new ArrayList<>()).add(edge.to);
		}

		for (int v=0; v<this.vertices; v++) {
			adj.computeIfAbsent(v, x -> new ArrayList<>());
		}

		return adj;
	}

	public Map<Integer, List<Edge>> adjacencyListWeighted() {
		Map<Integer, List<Edge>> adj = new HashMap<>();

		for (Edge edge : this.edges) {
			adj.computeIfAbsent(edge.from, x -> new ArrayList<>()).add(edge);
		}

		for (int v=0; v<this.vertices; v++) {
			adj.computeIfAbsent(v, x -> new ArrayList<>());
		}

		return adj;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.vertices, this.edges);
	}
}
