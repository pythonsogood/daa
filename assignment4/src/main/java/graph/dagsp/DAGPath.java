package graph.dagsp;

import java.util.Arrays;
import java.util.List;

import graph.instrumentation.TimedMetrics;
import graph.objects.Graph;
import graph.objects.Path;
import graph.topo.DFSTopo;

public class DAGPath {
	public static final TimedMetrics metrics = new TimedMetrics();
	private static long elapsed;

	public static long getElapsed() {
		return elapsed;
	}

	public static Path shortestPath(Graph graph, int source) {
		if (source >= graph.vertices) {
			throw new IllegalArgumentException("Source is out of range");
		}

		metrics.startTimer();
		metrics.reset();

		Integer[][] adj = graph.adjacencyArray();
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

			if (adj[u] == null) {
				continue;
			}

			for (int v=0; v<adj[u].length; v++) {
				if (adj[u][v] == null) {
					continue;
				}

				if (dist[v] == -1 || dist[v] > dist[u] + adj[u][v]) {
					dist[v] = dist[u] + adj[u][v];
					prev[v] = u;

					metrics.add("relaxation");
				}
			}
		}

		elapsed = metrics.stopTimer();

		return new Path(dist, prev);
	}

	public static Path longestPath(Graph graph, int source) {
		if (source >= graph.vertices) {
			throw new IllegalArgumentException("Source is out of range");
		}

		metrics.startTimer();
		metrics.reset();

		Integer[][] adj = graph.adjacencyArray();
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

			if (adj[u] == null) {
				continue;
			}

			for (int v=0; v<adj[u].length; v++) {
				if (adj[u][v] == null) {
					continue;
				}

				if (dist[v] == -1 || dist[v] < dist[u] + adj[u][v]) {
					dist[v] = dist[u] + adj[u][v];
					prev[v] = u;

					metrics.add("relaxation");
				}
			}
		}

		elapsed = metrics.stopTimer();

		return new Path(dist, prev);
	}
}
