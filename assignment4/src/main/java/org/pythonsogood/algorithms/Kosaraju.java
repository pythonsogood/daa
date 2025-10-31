package org.pythonsogood.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;

public class Kosaraju {
	private static void dfs(int vertex, boolean[] visited, Stack<Integer> stack, Graph graph, Map<Integer, List<Integer>> adj) {
		visited[vertex] = true;

		for (int i=0; i<graph.vertices; i++) {
			if (!visited[i] && adj.containsKey(vertex) && adj.get(vertex).contains(i)) {
				Kosaraju.dfs(i, visited, stack, graph, adj);
			}
		}

		stack.push(vertex);
	}

	private static List<Integer> dfsT(int vertex, boolean[] visited, Graph graph, Map<Integer, List<Integer>> adj, List<List<Integer>> sccs) {
		visited[vertex] = true;

		List<Integer> scc = new ArrayList<>();
		scc.add(vertex);

		for (int i=0; i<graph.vertices; i++) {
			if (!visited[i] && adj.containsKey(vertex) && adj.get(vertex).contains(i)) {
				List<Integer> dfsscc = Kosaraju.dfsT(i, visited, graph, adj, sccs);
				scc.addAll(dfsscc);
			}
		}

		scc.sort((a, b) -> a - b);

		return scc;
	}

	private static List<List<Integer>> scc(Graph graph) {
		List<List<Integer>> sccs = new ArrayList<>();

		Map<Integer, List<Integer>> adj = new HashMap<>();

		for (Edge edge : graph.edges) {
			if (!adj.containsKey(edge.from)) {
				adj.put(edge.from, new ArrayList<>(List.of(edge.to)));
			} else {
				adj.get(edge.from).add(edge.to);
			}
		}

		boolean[] visited = new boolean[graph.vertices];
		Stack<Integer> stack = new Stack<>();

		Arrays.fill(visited, false);

		for (int i=0; i<graph.vertices; i++) {
			if (!visited[i]) {
				Kosaraju.dfs(i, visited, stack, graph, adj);
			}
		}

		Graph transposed = graph.transposed();

		Map<Integer, List<Integer>> adjT = new HashMap<>();

		for (Edge edge : transposed.edges) {
			if (!adjT.containsKey(edge.from)) {
				adjT.put(edge.from, new ArrayList<>(List.of(edge.to)));
			} else {
				adjT.get(edge.from).add(edge.to);
			}
		}

		for (int i=0; i<graph.vertices; i++) {
			visited[i] = false;
		}

		while (!stack.empty()) {
			int v = stack.pop();
			if (!visited[v]) {
				List<Integer> scc = dfsT(v, visited, transposed, adjT, sccs);
				sccs.add(scc);
			}
		}

		sccs.sort((a, b) -> a.get(0) - b.get(0));

		return sccs;
	}

	public static List<List<Integer>> SCC(Graph graph) {
		return Kosaraju.scc(graph);
	}
}
