package org.pythonsogood.models;

import java.util.List;

public class InputJson {
	public List<GraphJson> graphs;

	public InputJson(List<GraphJson> graphs) {
		this.graphs = graphs;
	}

	@Override
	public String toString() {
		return String.format("%s", this.graphs);
	}
}
