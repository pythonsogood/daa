package org.pythonsogood.graph;

import java.util.ArrayList;
import java.util.List;

import org.pythonsogood.models.InputEdge;
import org.pythonsogood.models.InputJson;

public class Graph {
	public int vertices;
	public List<Edge> edges;

	public Graph(int vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public Graph(List<Edge> edges) {
		this.edges = edges;

		this.vertices = 0;
		for (Edge edge : edges) {
			this.vertices = Math.max(Math.max(edge.from, this.vertices), edge.to) + 1;
		}
	}

	public Graph(InputJson inputJson) {
		List<Edge> edges = new ArrayList<>();

		for (InputEdge edgeJson : inputJson.edges) {
			edges.add(new Edge(edgeJson));
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

	@Override
	public String toString() {
		return String.format("%s %s", this.vertices, this.edges);
	}
}
