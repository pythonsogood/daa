package graph.objects;

import java.util.ArrayList;
import java.util.List;

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

	public Integer[][] adjacencyArray() {
		Integer[][] adj = new Integer[this.vertices][this.vertices];

		for (Edge edge : this.edges) {
			adj[edge.from][edge.to] = edge.weight;
		}

		return adj;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.vertices, this.edges);
	}
}
