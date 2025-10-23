package org.pythonsogood.models;

import org.pythonsogood.graph.Graph;

public class OutputResultJson {
	public int graph_id;
	public InputStatsJson input_stats;
	public OutputAlgorithmJson prim;
	public OutputAlgorithmJson kruskal;

	public OutputResultJson(int graph_id, InputStatsJson input_stats, OutputAlgorithmJson prim, OutputAlgorithmJson kruskal) {
		this.graph_id = graph_id;
		this.input_stats = input_stats;
		this.prim = prim;
		this.kruskal = kruskal;
	}

	public OutputResultJson(int graph_id, Graph graph, OutputAlgorithmJson prim, OutputAlgorithmJson kruskal) {
		this.graph_id = graph_id;
		this.input_stats = new InputStatsJson(graph);
		this.prim = prim;
		this.kruskal = kruskal;
	}

	@Override
	public String toString() {
		return String.format("Graph %s (%s): %s | %s", this.graph_id, this.input_stats, this.prim, this.kruskal);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputResultJson outputResultJson) {
			return outputResultJson.graph_id == this.graph_id && outputResultJson.input_stats.equals(this.input_stats) && outputResultJson.prim.equals(this.prim) && outputResultJson.kruskal.equals(this.kruskal);
		} else {
			return false;
		}
	}

	public boolean equals(Object o, boolean onlyAlgorithm) {
		if (o instanceof OutputResultJson outputResultJson) {
			return outputResultJson.graph_id == this.graph_id && outputResultJson.input_stats.equals(this.input_stats) && outputResultJson.prim.equals(this.prim, onlyAlgorithm) && outputResultJson.kruskal.equals(this.kruskal, onlyAlgorithm);
		} else {
			return false;
		}
	}
}
