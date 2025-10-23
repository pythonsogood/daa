package org.pythonsogood;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pythonsogood.algorithms.Kruskal;
import org.pythonsogood.algorithms.Prim;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class EdgeJson {
	String from;
	String to;
	int weight;

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
			return ((edgeJson.from.equals(this.from) && edgeJson.to.equals(this.to)) || (edgeJson.from.equals(this.to) && edgeJson.to.equals(this.from)) && edgeJson.weight == this.weight);
		} else {
			return false;
		}
	}
}

class GraphJson {
	int id;
	List<String> nodes;
	List<EdgeJson> edges;

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

class InputJson {
	List<GraphJson> graphs;

	@Override
	public String toString() {
		return String.format("%s", this.graphs);
	}
}

class InputStatsJson {
	int vertices;
	int edges;

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

class OutputAlgorithmJson {
	List<EdgeJson> mst_edges;
	int total_cost;
	int operations_count;
	float execution_time_ms;

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

class OutputResultJson {
	int graph_id;
	InputStatsJson input_stats;
	OutputAlgorithmJson prim;
	OutputAlgorithmJson kruskal;

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

class OutputJson {
	List<OutputResultJson> results;

	public OutputJson(List<OutputResultJson> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return String.format("%s", this.results);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputJson outputJson) {
			return outputJson.results.equals(this.results);
		} else {
			return false;
		}
	}

	public boolean equals(Object o, boolean onlyAlgorithm) {
		if (o instanceof OutputJson outputJson) {
			if (this.results.size() != outputJson.results.size()) {
				return false;
			}

			for (int i=0; i<this.results.size(); i++) {
				if (!this.results.get(i).equals(outputJson.results.get(i), onlyAlgorithm)) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}
}

public class Main {
    public static void main(String[] args) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		FileReader inputReader = new FileReader("sample/input.json");
		InputJson input = gson.fromJson(inputReader, InputJson.class);
		inputReader.close();

		List<OutputResultJson> outputResults = new ArrayList<>();

		for (int i=0; i<input.graphs.size(); i++) {
			GraphJson graphJson = input.graphs.get(i);
			Graph graph = new Graph(graphJson);

			long startTime = System.nanoTime();

			List<Edge> kruskal = Kruskal.mst(graph);

			float kruskalExecutionTime = (System.nanoTime() - startTime) / 1000000.0f;

			startTime = System.nanoTime();

			List<Edge> prim = Prim.mst(graph);

			float primExecutionTime = (System.nanoTime() - startTime) / 1000000.0f;

			OutputResultJson outputResultJson = new OutputResultJson(graphJson.id, graph, new OutputAlgorithmJson(prim, Prim.getOperations(), primExecutionTime), new OutputAlgorithmJson(kruskal, Kruskal.getOperations(), kruskalExecutionTime));

			outputResults.add(outputResultJson);
		}

		OutputJson output = new OutputJson(outputResults);

		FileWriter outputWriter = new FileWriter("output.json");
		gson.toJson(output, outputWriter);
		outputWriter.close();

		FileReader sampleOutputReader = new FileReader("sample/output.json");
		OutputJson sampleOutput = gson.fromJson(sampleOutputReader, OutputJson.class);
		sampleOutputReader.close();

		System.out.println(output.equals(sampleOutput, true));
    }
}