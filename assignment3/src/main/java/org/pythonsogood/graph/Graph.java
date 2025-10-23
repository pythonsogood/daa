package org.pythonsogood.graph;

import java.util.ArrayList;
import java.util.List;

import org.pythonsogood.models.EdgeJson;
import org.pythonsogood.models.GraphJson;

public class Graph {
	public List<Vertex> vertices;
	public List<Edge> edges;

	public Graph(List<Vertex> vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public Graph(GraphJson graphJson) {
		List<Vertex> vertices = new ArrayList<>();

		for (String node : graphJson.nodes) {
			vertices.add(new Vertex(node));
		}

		List<Edge> edges = new ArrayList<>();

		for (EdgeJson edgeJson : graphJson.edges) {
			edges.add(new Edge(edgeJson));
		}

		this.vertices = vertices;
		this.edges = edges;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.vertices, this.edges);
	}
}
