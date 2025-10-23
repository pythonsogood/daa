package org.pythonsogood;

import java.util.ArrayList;
import java.util.List;

import org.pythonsogood.algorithms.Kruskal;
import org.pythonsogood.algorithms.Prim;
import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.interfaces.MSTAlgorithm;
import org.pythonsogood.models.GraphJson;
import org.pythonsogood.models.InputJson;
import org.pythonsogood.models.OutputAlgorithmJson;
import org.pythonsogood.models.OutputJson;
import org.pythonsogood.models.OutputResultJson;

public class Runner {
	private Kruskal kruskal = new Kruskal();
	private Prim prim = new Prim();

	public Runner() {}

	public Kruskal getKruskal() {
		return this.kruskal;
	}

	public Prim getPrim() {
		return this.prim;
	}

	public OutputAlgorithmJson runAlgorithm(MSTAlgorithm algorithm, Graph graph) {
		long startTime = System.nanoTime();

		List<Edge> mst = algorithm.mst(graph);

		float executionTime = (System.nanoTime() - startTime) / 1000000.0f;

		return new OutputAlgorithmJson(mst, algorithm.getOperations(), executionTime);
	}

	public OutputResultJson runGraph(GraphJson graphJson) {
		Graph graph = new Graph(graphJson);

		return new OutputResultJson(
			graphJson.id,
			graph,
			this.runAlgorithm(this.prim, graph),
			this.runAlgorithm(this.kruskal, graph)
		);
	}

	public OutputJson runInput(InputJson inputJson) {
		List<OutputResultJson> outputResultsJson = new ArrayList<>();

		for (GraphJson graphJson : inputJson.graphs) {
			outputResultsJson.add(this.runGraph(graphJson));
		}

		return new OutputJson(outputResultsJson);
	}
}
