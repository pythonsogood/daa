package org.pythonsogood.graph;

import java.util.List;

import org.pythonsogood.models.EdgeJson;

public class Edge {
	public String from;
	public String to;
	public int weight;

	public Edge(String from, String to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Edge(EdgeJson edgeJson) {
		this.from = edgeJson.from;
		this.to = edgeJson.to;
		this.weight = edgeJson.weight;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s (%s)", this.from, this.to, this.weight);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge edge) {
			return ((edge.from.equals(this.from) && edge.to.equals(this.to)) || (edge.from.equals(this.to) && edge.to.equals(this.from))) && edge.weight == this.weight;
		} else {
			return false;
		}
	}

	static public int getCost(List<Edge> edges) {
		int cost = 0;

		for (Edge e : edges) {
			cost += e.weight;
		}

		return cost;
	}
}
