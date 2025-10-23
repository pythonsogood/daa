package org.pythonsogood.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.graph.Vertex;
import org.pythonsogood.interfaces.MSTAlgorithm;

public class Kruskal extends MSTAlgorithm {
	public List<Edge> mst(Graph graph) {
		this.resetOperations();

		int vertexCount = graph.vertices.size();

		HashMap<String, Integer> vertexMap = new HashMap<>();

		for (int i=0; i<vertexCount; i++) {
			Vertex v = graph.vertices.get(i);
			vertexMap.put(v.name, i);
		}

		List<Edge> edges = new ArrayList<>(graph.edges);
		edges.sort((Edge e1, Edge e2) -> {
			this.operations++;

			return e1.weight - e2.weight;
		});

		DSU dsu = new DSU(vertexCount);

		List<Edge> mst = new ArrayList<>();

		for (Edge e : edges) {
			int x = vertexMap.get(e.from);
			int y = vertexMap.get(e.to);

			if (dsu.find(x) != dsu.find(y)) {
				dsu.union(x, y);
				mst.add(e);

				if (mst.size() == vertexCount - 1) {
					break;
				}
			}
		}

		return mst;
	}

	private class DSU {
		public int[] parent;
		public int[] rank;

		public DSU(int size) {
			this.parent = new int[size];
			this.rank = new int[size];

			for (int i=0; i<size; i++) {
				this.parent[i] = i;
				this.rank[i] = 1;
			}
		}

		public int find(int i) {
			Kruskal.this.operations++;

			if (parent[i] != i) {
				parent[i] = this.find(parent[i]);
			}

			return parent[i];
		}

		public void union(int x, int y) {
			Kruskal.this.operations++;

			int s1 = this.find(x);
			int s2 = this.find(y);

			if (s1 != s2) {
				if (this.rank[s1] < this.rank[s2]) {
					this.parent[s1] = s2;
				} else if (this.rank[s1] > this.rank[s2]) {
					this.parent[s2] = s1;
				} else {
					this.parent[s2] = s1;
					this.rank[s2]++;
				}
			}
		}
	}
}
