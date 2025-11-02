package graph.objects;

import java.util.ArrayList;
import java.util.List;

public class DAGSPath {
	public final int[] distances;
	public final int[] previous;

	public DAGSPath(int[] distances, int[] previous) {
		this.distances = distances;
		this.previous = previous;
	}

	public List<List<Integer>> construct() {
		List<List<Integer>> paths = new ArrayList<>();

		for (int i=0; i<this.distances.length; i++) {
			if (this.distances[i] == -1) {
				paths.add(null);
				continue;
			}

			List<Integer> path = new ArrayList<>();
			int j = i;
			while (j != -1) {
				path.add(0, j);
				j = this.previous[j];
			}

			paths.add(path);
		}

		return paths;
	}

	@Override
	public String toString() {
		return String.format("%s", this.construct());
	}
}
