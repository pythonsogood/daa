package org.pythonsogood.models;

import java.util.List;

import org.pythonsogood.graph.Graph;

public class GraphJson {
	public int id;
	public List<String> nodes;
	public List<EdgeJson> edges;

	public GraphJson(int id, List<String> nodes, List<EdgeJson> edges) {
		this.id = id;
		this.nodes = nodes;
		this.edges = edges;
	}

	public GraphJson(int id, Graph graph) {
		this.id = id;
		this.nodes = graph.vertices.stream().map((v) -> v.name).toList();
		this.edges = graph.edges.stream().map(EdgeJson::new).toList();
	}

	@Override
	public String toString() {
		return String.format("Graph %s %s: %s", this.id, this.nodes, this.edges);
	}
}
