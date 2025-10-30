package org.pythonsogood.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.pythonsogood.graph.Graph;

public class Tarjan {
	private static void visit(int vertex, int[] lowlink, int[] visited, boolean[] inStack, Stack<Integer> stack, Integer time, List<List<Integer>> sccs) {
		visited[vertex] = time;
		lowlink[vertex] = time;

		time++;

		inStack[vertex] = true;
		stack.push(vertex);
	}

	public static List<List<Integer>> SCC(Graph graph) {
		// TODO: adjacency list and pass it to visit function

		List<List<Integer>> sccs = new ArrayList<>();

		int visited[] = new int[graph.vertices];
		int lowlink[] = new int[graph.vertices];

		Arrays.fill(visited, -1);
		Arrays.fill(lowlink, -1);

		boolean inStack[] = new boolean[graph.vertices];
		Stack<Integer> stack = new Stack<>();

		Integer time = 0;

		for (int i=0; i<graph.vertices; i++) {
			if (visited[i] == -1) {
				Tarjan.visit(i, lowlink, visited, inStack, stack, time, sccs);
			}
		}

		return sccs;
	}
}
