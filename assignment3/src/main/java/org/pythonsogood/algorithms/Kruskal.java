package org.pythonsogood.algorithms;

import org.pythonsogood.Graph;
import org.pythonsogood.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pythonsogood.Edge;

public class Kruskal {
	private static int operations = 0;

	public static int getOperations() {
		return operations;
	}

	public static List<Edge> mst(Graph graph) {
		Kruskal.operations = 0;

		int vertexCount = graph.vertices.size();
		int edgeCount = graph.edges.size();

		HashMap<String, Integer> vertexMap = new HashMap<>();

		for (int i=0; i<vertexCount; i++) {
			Vertex v = graph.vertices.get(i);
			vertexMap.put(v.name, i);
		}

		List<Edge> edges = new ArrayList<>(graph.edges);
		edges.sort((Edge e1, Edge e2) -> e1.weight - e2.weight);

		// edges.sort() ~ O(n log(n))
		Kruskal.operations += edgeCount * Math.log(edgeCount) / Math.log(2);

		DSU dsu = new DSU(vertexCount);

		List<Edge> mst = new ArrayList<>();

		for (Edge e : edges) {
			int x = vertexMap.get(e.from);
			int y = vertexMap.get(e.to);

			Kruskal.operations += 2;

			if (dsu.find(x) != dsu.find(y)) {
				dsu.union(x, y);
				mst.add(e);

				Kruskal.operations += 2;

				if (mst.size() == vertexCount - 1) {
					break;
				}
			}
		}

		return mst;
	}

	public static class DSU {
		public int[] parent;
		public int[] rank;

		public DSU(int size) {
			this.parent = new int[size];
			this.rank = new int[size];

			for (int i=0; i<size; i++) {
				this.parent[i] = i;
				this.rank[i] = 1;

				Kruskal.operations += 2;
			}
		}

		public int find(int i) {
			if (parent[i] != i) {
				parent[i] = this.find(parent[i]);

				Kruskal.operations += 2;
			}

			Kruskal.operations =+ 2;

			return parent[i];
		}

		public void union(int x, int y) {
			int s1 = this.find(x);
			int s2 = this.find(y);

			Kruskal.operations++;

			if (s1 != s2) {
				Kruskal.operations += 4;

				if (this.rank[s1] < this.rank[s2]) {
					this.parent[s1] = s2;

					Kruskal.operations++;
				} else if (this.rank[s1] > this.rank[s2]) {
					this.parent[s2] = s1;

					Kruskal.operations += 4;
				} else {
					this.parent[s2] = s1;
					this.rank[s2]++;

					Kruskal.operations += 3;
				}
			}
		}
	}
}
