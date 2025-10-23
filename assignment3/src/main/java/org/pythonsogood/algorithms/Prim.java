package org.pythonsogood.algorithms;

import org.pythonsogood.Graph;
import org.pythonsogood.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pythonsogood.Edge;

public class Prim {
	private static int operations = 0;

	public static int getOperations() {
		return operations;
	}

	public static List<Edge> mst(Graph graph) {
		Prim.operations = 0;

		int vertexCount = graph.vertices.size();

		HashMap<String, Integer> vertexMap = new HashMap<>();

		for (int i=0; i<vertexCount; i++) {
			Vertex v = graph.vertices.get(i);
			vertexMap.put(v.name, i);
		}

		int[][] matrix = new int[vertexCount][vertexCount];
		for (Edge e : graph.edges) {
			int u = vertexMap.get(e.from);
			int v = vertexMap.get(e.to);
			matrix[u][v] = e.weight;
			matrix[v][u] = e.weight;

			Prim.operations += 4;
		}

		int[] parent = new int[vertexCount];
		int[] key = new int[vertexCount];
		boolean[] mstSet = new boolean[vertexCount];

		for (int i=0; i<vertexCount; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;

			Prim.operations += 2;
		}

		key[0] = 0;
		parent[0] = -1;

		Prim.operations += 2;

		for (int count=0; count < vertexCount - 1; count++) {
			int u = Prim.minKey(key, mstSet);

			Prim.operations++;

			if (u == -1) {
				break;
			}

			mstSet[u] = true;

			Prim.operations++;

			for (int v=0; v<vertexCount; v++) {
				if (matrix[u][v] != 0 && mstSet[v] == false && matrix[u][v] < key[v]) {
					parent[v] = u;
					key[v] = matrix[u][v];

					Prim.operations += 3;
				}

				Prim.operations += 9;
			}
		}

		List<Edge> mst = new ArrayList<>();

		for (int i=1; i<vertexCount; i++) {
			int u = parent[i];
			int weight = matrix[i][u];

			if (weight > 0) {
				mst.add(new Edge(graph.vertices.get(u).name, graph.vertices.get(i).name, weight));
			}
		}

		return mst;
	}

	private static int minKey(int key[], boolean mstSet[]) {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;

		for (int v=0; v<mstSet.length; v++) {
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				minIndex = v;

				Prim.operations += 3;
			}

			Prim.operations += 4;
		}

		return minIndex;
	}
}
