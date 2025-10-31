package org.pythonsogood.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;

public class Tarjan {
	private static void visit(int vertex, int[] lowlink, int[] visited, boolean[] inStack, Stack<Integer> stack, Map<Integer, List<Integer>> adj, Integer time, List<List<Integer>> sccs) {
		visited[vertex] = time;
		lowlink[vertex] = time;

		time++;

		inStack[vertex] = true;
		stack.push(vertex);

		if (adj.containsKey(vertex)) {
			for (int n : adj.get(vertex)) {
				if (visited[n] == -1) {
					Tarjan.visit(n, lowlink, visited, inStack, stack, adj, time, sccs);

					lowlink[vertex] = Math.min(lowlink[vertex], lowlink[n]);
				} else if (inStack[n] == true) {
					lowlink[vertex] = Math.min(lowlink[vertex], visited[n]);
				}
			}
		}

		int w = -1;
		if (lowlink[vertex] == visited[vertex]) {
			List<Integer> scc = new ArrayList<>();

			while (w != vertex) {
				w = (int) stack.pop();
				scc.add(w);
				inStack[w] = false;
			}

			scc.sort((a, b) -> a - b);

			sccs.add(scc);
		}
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

		int visited[] = new int[graph.vertices];
		int lowlink[] = new int[graph.vertices];

		Arrays.fill(visited, -1);
		Arrays.fill(lowlink, -1);

		boolean inStack[] = new boolean[graph.vertices];
		Stack<Integer> stack = new Stack<>();

		Integer time = 0;

		for (int i=0; i<graph.vertices; i++) {
			if (visited[i] == -1) {
				Tarjan.visit(i, lowlink, visited, inStack, stack, adj, time, sccs);
			}
		}

		return sccs;
	}

	public static List<List<Integer>> SCC(Graph graph) {
		return Tarjan.scc(graph);
	}
}
