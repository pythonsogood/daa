package org.pythonsogood.models;

import java.util.List;

import org.pythonsogood.graph.Edge;

public class OutputAlgorithmJson {
	public List<EdgeJson> mst_edges;
	public int total_cost;
	public int operations_count;
	public float execution_time_ms;

	public OutputAlgorithmJson(List<EdgeJson> mst_edges, int total_cost, int operations_count, float execution_time_ms) {
		this.mst_edges = mst_edges;
		this.total_cost = total_cost;
		this.operations_count = operations_count;
		this.execution_time_ms = execution_time_ms;
	}

	public OutputAlgorithmJson(List<Edge> mst_edges, int operations_count, float execution_time_ms) {
		this.mst_edges = mst_edges.stream().map(EdgeJson::new).toList();
		this.total_cost = Edge.getCost(mst_edges);
		this.operations_count = operations_count;
		this.execution_time_ms = execution_time_ms;
	}

	@Override
	public String toString() {
		return String.format("%s (%s) | %s %s", this.mst_edges, this.total_cost, this.operations_count, this.execution_time_ms);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputAlgorithmJson outputAlgorithmJson) {
			return outputAlgorithmJson.mst_edges.equals(this.mst_edges) && outputAlgorithmJson.total_cost == this.total_cost && outputAlgorithmJson.operations_count == this.operations_count && outputAlgorithmJson.execution_time_ms == this.execution_time_ms;
		} else {
			return false;
		}
	}

	public boolean equals(Object o, boolean onlyAlgorithm) {
		if (!onlyAlgorithm) {
			return this.equals(o);
		}

		if (o instanceof OutputAlgorithmJson outputAlgorithmJson) {
			return outputAlgorithmJson.mst_edges.equals(this.mst_edges) && outputAlgorithmJson.total_cost == this.total_cost;
		} else {
			return false;
		}
	}
}
