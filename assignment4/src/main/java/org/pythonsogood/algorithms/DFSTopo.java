package org.pythonsogood.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;

public class DFSTopo {
	private static void visit(int vertex, boolean[] visited, Map<Integer, List<Integer>> adj, Stack<Integer> stack) {
		visited[vertex] = true;

		if (adj.containsKey(vertex)) {
			for (int u : adj.get(vertex)) {
				if (!visited[u]) {
					DFSTopo.visit(u, visited, adj, stack);
				}
			}
		}

		stack.push(vertex);
	}

	public static List<Integer> sort(Graph graph) {
		Map<Integer, List<Integer>> adj = new HashMap<>();

		for (Edge edge : graph.edges) {
			if (!adj.containsKey(edge.from)) {
				adj.put(edge.from, new ArrayList<>(List.of(edge.to)));
			} else {
				adj.get(edge.from).add(edge.to);
			}
		}

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
			topo.add(stack.pop());
		}

		return topo;
	}
}
