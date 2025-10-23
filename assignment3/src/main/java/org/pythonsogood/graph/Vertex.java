package org.pythonsogood.graph;

public class Vertex {
	public String name;

	public Vertex(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vertex vertex) {
			return vertex.name.equals(this.name);
		} else if (o instanceof String string) {
			return string.equals(this.name);
		} else {
			return false;
		}
	}
}
