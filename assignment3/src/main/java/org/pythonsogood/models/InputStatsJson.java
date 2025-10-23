package org.pythonsogood.models;

import org.pythonsogood.graph.Graph;

public class InputStatsJson {
	public int vertices;
	public int edges;

	public InputStatsJson(int vertices, int edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public InputStatsJson(Graph graph) {
		this.vertices = graph.vertices.size();
		this.edges = graph.edges.size();
	}

	@Override
	public String toString() {
		return String.format("%s vertices, %s edges", this.vertices, this.edges);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof InputStatsJson inputStatsJson) {
			return inputStatsJson.vertices == this.vertices && inputStatsJson.edges == this.edges;
		} else {
			return false;
		}
	}
}
