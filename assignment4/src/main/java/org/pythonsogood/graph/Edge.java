package org.pythonsogood.graph;

import java.util.List;

import org.pythonsogood.models.InputEdge;

public class Edge {
	public int from;
	public int to;
	public int weight;

	public Edge(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Edge(InputEdge edgeJson) {
		this.from = edgeJson.u;
		this.to = edgeJson.v;
		this.weight = edgeJson.w;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s (%s)", this.from, this.to, this.weight);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge edge) {
			return ((edge.from == this.from && edge.to == this.to) || (edge.from == this.to && edge.to == this.from)) && edge.weight == this.weight;
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
