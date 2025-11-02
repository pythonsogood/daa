package graph.dagsp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import graph.instrumentation.TimedMetrics;
import graph.objects.Graph;
import graph.objects.DAGSPath;
import graph.objects.Edge;
import graph.topo.DFSTopo;

public class DAGPath {
	public static final TimedMetrics metrics = new TimedMetrics();
	private static long elapsed;

	public static long getElapsed() {
		return elapsed;
	}

	public static DAGSPath shortestPath(Graph graph, int source) {
		if (source >= graph.vertices) {
			throw new IllegalArgumentException("Source is out of range");
		}

		metrics.startTimer();
		metrics.reset();

		Map<Integer, List<Edge>> adj = graph.adjacencyListWeighted();
		List<Integer> topo = DFSTopo.sort(graph);

		int[] dist = new int[graph.vertices];
		int[] prev = new int[graph.vertices];

		Arrays.fill(dist, -1);
		Arrays.fill(prev, -1);

		dist[source] = 0;

		for (int u : topo) {
			if (dist[u] == -1) {
				continue;
			}

			for (Edge edge : adj.get(u)) {
				if (dist[edge.to] == -1 || dist[edge.to] > dist[u] + edge.weight) {
					dist[edge.to] = dist[u] + edge.weight;
					prev[edge.to] = u;

					metrics.add("relaxation");
				}
			}
		}

		elapsed = metrics.stopTimer();

		return new DAGSPath(dist, prev);
	}

	public static DAGSPath longestPath(Graph graph, int source) {
		if (source >= graph.vertices) {
			throw new IllegalArgumentException("Source is out of range");
		}

		metrics.startTimer();
		metrics.reset();

		Map<Integer, List<Edge>> adj = graph.adjacencyListWeighted();
		List<Integer> topo = DFSTopo.sort(graph);

		int[] dist = new int[graph.vertices];
		int[] prev = new int[graph.vertices];

		Arrays.fill(dist, -1);
		Arrays.fill(prev, -1);

		dist[source] = 0;

		for (int u : topo) {
			if (dist[u] == -1) {
				continue;
			}

			for (Edge edge : adj.get(u)) {
				if (dist[edge.to] == -1 || dist[edge.to] < dist[u] + edge.weight) {
					dist[edge.to] = dist[u] + edge.weight;
					prev[edge.to] = u;

					metrics.add("relaxation");
				}
			}
		}

		elapsed = metrics.stopTimer();

		return new DAGSPath(dist, prev);
	}
}
