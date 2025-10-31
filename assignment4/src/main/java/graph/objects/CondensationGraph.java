package graph.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CondensationGraph {
	public final Graph graph;
	public final List<List<Integer>> sccs;
	public final List<Edge> edges;
	public final Map<Integer, Integer> vertexToSCC;

	public CondensationGraph(Graph graph, List<List<Integer>> sccs) {
		this.graph = graph;
		this.sccs = sccs;

		Map<Integer, Integer> vertexToSCC = new HashMap<>();
		for (int i=0; i<sccs.size(); i++) {
			for (int v : sccs.get(i)) {
				vertexToSCC.put(v, i);
			}
		}

		this.vertexToSCC = vertexToSCC;

		Edge[][] connectedSCCs = new Edge[sccs.size()][sccs.size()];
		for (Edge e : graph.edges) {
			int u = vertexToSCC.get(e.from);
			int v = vertexToSCC.get(e.to);

			if (u != v && (connectedSCCs[u][v] == null || connectedSCCs[u][v].weight > e.weight)) {
				Edge edge = new Edge(u, v, e.weight);
				connectedSCCs[u][v] = edge;
			}
		}

		List<Edge> edges = new ArrayList<>();

		for (int i=0; i<connectedSCCs.length; i++) {
			for (int j=0; j<connectedSCCs[i].length; j++) {
				if (connectedSCCs[i][j] != null) {
					edges.add(connectedSCCs[i][j]);
				}
			}
		}

		this.edges = edges;
	}

	public Graph toGraph() {
		return new Graph(this.edges);
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.sccs, this.edges);
	}
}
