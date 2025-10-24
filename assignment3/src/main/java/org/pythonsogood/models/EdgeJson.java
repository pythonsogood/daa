package org.pythonsogood.models;

import java.util.List;

import org.pythonsogood.graph.Edge;

public class EdgeJson {
	public String from;
	public String to;
	public int weight;

	public EdgeJson(String from, String to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public EdgeJson(Edge edge) {
		this.from = edge.from;
		this.to = edge.to;
		this.weight = edge.weight;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s (%s)", this.from, this.to, this.weight);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EdgeJson edgeJson) {
			return ((edgeJson.from.equals(this.from) && edgeJson.to.equals(this.to)) || (edgeJson.from.equals(this.to) && edgeJson.to.equals(this.from))) && edgeJson.weight == this.weight;
		} else {
			return false;
		}
	}

	static public int getCost(List<EdgeJson> edges) {
		int cost = 0;

		for (EdgeJson e : edges) {
			cost += e.weight;
		}

		return cost;
	}
}
