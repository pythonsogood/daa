package graph.serialization;

import java.util.List;

public class InputJson {
	public boolean directed;
	public int n;
	public List<InputEdge> edges;
	public int source;
	public String weight_model;

	public InputJson(boolean directed, int n, List<InputEdge> edges, int source, String weight_model) {
		this.directed = directed;
		this.n = n;
		this.edges = edges;
		this.source = source;
		this.weight_model = weight_model;
	}

	public InputJson(int n, List<InputEdge> edges, int source) {
		this.directed = true;
		this.n = n;
		this.edges = edges;
		this.source = source;
		this.weight_model = "edge";
	}

	@Override
	public String toString() {
		return "InputJson{" +
				"directed=" + directed +
				", n=" + n +
				", edges=" + edges +
				", source=" + source +
				", weight_model='" + weight_model + '\'' +
				'}';
	}
}
