package graph.scc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import graph.instrumentation.TimedMetrics;
import graph.objects.Graph;

public class Kosaraju {
	public static final TimedMetrics metrics = new TimedMetrics();
	private static long elapsed;

	public static long getElapsed() {
		return elapsed;
	}

	private static void dfs(int vertex, boolean[] visited, Stack<Integer> stack, Integer[][] adj) {
		metrics.add("dfs");

		visited[vertex] = true;

		for (int i=0; i<adj.length; i++) {
			if (adj[vertex][i] != null && !visited[i]) {
				Kosaraju.dfs(i, visited, stack, adj);
			}
		}

		stack.push(vertex);
	}

	private static void dfsT(int vertex, boolean[] visited, Integer[][] adj, List<Integer> scc) {
		metrics.add("dfs");

		visited[vertex] = true;

		scc.add(vertex);

		for (int i=0; i<adj.length; i++) {
			if (adj[vertex][i] != null && !visited[i]) {
				Kosaraju.dfsT(i, visited, adj, scc);
			}
		}
	}

	public static List<List<Integer>> scc(Graph graph) {
		metrics.startTimer();
		metrics.reset();

		List<List<Integer>> sccs = new ArrayList<>();

		Integer[][] adj = graph.adjacencyArray();
		Stack<Integer> stack = new Stack<>();
		boolean[] visited = new boolean[graph.vertices];

		Arrays.fill(visited, false);

		for (int i=0; i<graph.vertices; i++) {
			if (!visited[i]) {
				Kosaraju.dfs(i, visited, stack, adj);
			}
		}

		Arrays.fill(visited, false);

		Graph transposed = graph.transposed();
		Integer[][] adjT = transposed.adjacencyArray();

		while (!stack.empty()) {
			int v = stack.pop();
			if (!visited[v]) {
				List<Integer> scc = new ArrayList<>();

				dfsT(v, visited, adjT, scc);
				scc.sort((a, b) -> a - b);

				sccs.add(scc);
			}
		}

		sccs.sort((a, b) -> a.get(0) - b.get(0));

		elapsed = metrics.stopTimer();

		return sccs;
	}
}
