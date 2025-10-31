package graph.objects;

import graph.serialization.InputEdge;

public class Edge {
	public final int from;
	public final int to;
	public final int weight;

	public Edge(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Edge(int from, int to) {
		this.from = from;
		this.to = to;
		this.weight = 0;
	}

	public Edge(InputEdge edgeJson) {
		this.from = edgeJson.u;
		this.to = edgeJson.v;
		this.weight = edgeJson.w;
	}

	public Edge reversed() {
		return new Edge(this.to, this.from, this.weight);
	}

	@Override
	public String toString() {
		if (this.weight == 0 && this.from != this.to) {
			return String.format("%s -> %s", this.from, this.to);
		}

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
}
