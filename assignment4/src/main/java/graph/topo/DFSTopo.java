package graph.topo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import graph.instrumentation.TimedMetrics;
import graph.objects.CondensationGraph;
import graph.objects.Graph;

public class DFSTopo {
	public static final TimedMetrics metrics = new TimedMetrics();
	private static long elapsed;

	public static long getElapsed() {
		return elapsed;
	}

	private static void visit(int vertex, boolean[] visited, Map<Integer, List<Integer>> adj, Stack<Integer> stack) {
		metrics.add("dfs");

		visited[vertex] = true;

		for (Integer v : adj.get(vertex)) {
			if (!visited[v]) {
				DFSTopo.visit(v, visited, adj, stack);
			}
		}

		metrics.add("push");

		stack.push(vertex);
	}

	public static List<Integer> sort(Graph graph) {
		metrics.startTimer();
		metrics.reset();

		Map<Integer, List<Integer>> adj = graph.adjacencyList();
		Stack<Integer> stack = new Stack<>();
		boolean[] visited = new boolean[graph.vertices];

		Arrays.fill(visited, false);

		for (int i=0; i<graph.vertices; i++) {
			if (!visited[i]) {
				DFSTopo.visit(i, visited, adj, stack);
			}
		}

		ArrayList<Integer> topo = new ArrayList<>();

		while (!stack.empty()) {
			metrics.add("pop");

			topo.add(stack.pop());
		}

		elapsed = metrics.stopTimer();

		return topo;
	}

	public static List<Integer> sort(CondensationGraph condensationGraph, boolean decompress) {
		List<Integer> topo = DFSTopo.sort(condensationGraph.toGraph());

		if (!decompress) {
			return topo;
		}

		List<Integer> topoDecompressed = new ArrayList<>();

		for (int scc : topo) {
			topoDecompressed.addAll(condensationGraph.sccs.get(scc));
		}

		return topoDecompressed;
	}

	public static List<Integer> sort(CondensationGraph condensationGraph) {
		return DFSTopo.sort(condensationGraph, false);
	}
}
