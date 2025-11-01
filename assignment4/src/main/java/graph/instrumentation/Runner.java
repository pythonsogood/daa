package graph.instrumentation;

import java.util.List;

import graph.dagsp.DAGPath;
import graph.objects.CondensationGraph;
import graph.objects.Graph;
import graph.objects.Path;
import graph.scc.Kosaraju;
import graph.topo.DFSTopo;

public class Runner {
	public Runner() {}

	public TimedOutput<List<List<Integer>>> scc(Graph graph) {
		return new TimedOutput<>(Kosaraju.scc(graph), Kosaraju.getElapsed());
	}

	public TimedOutput<List<Integer>> topo(Graph graph) {
		return new TimedOutput<>(DFSTopo.sort(graph), DFSTopo.getElapsed());
	}

	public TimedOutput<List<Integer>> topo(CondensationGraph condensationGraph, boolean decompress) {
		return new TimedOutput<>(DFSTopo.sort(condensationGraph, decompress), DFSTopo.getElapsed());
	}

	public TimedOutput<Path> shortestPath(Graph graph, int source) {
		return new TimedOutput<>(DAGPath.shortestPath(graph, source), DAGPath.getElapsed());
	}

	public TimedOutput<Path> longestPath(Graph graph, int source) {
		return new TimedOutput<>(DAGPath.longestPath(graph, source), DAGPath.getElapsed());
	}
}
