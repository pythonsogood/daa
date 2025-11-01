package graph.serialization;

public class InputEdge {
	public int u;
	public int v;
	public int w;

	public InputEdge(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	public InputEdge(int u, int v) {
		this.u = u;
		this.v = v;
		this.w = 0;
	}
}
