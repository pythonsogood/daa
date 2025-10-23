package org.pythonsogood.models;

import java.util.List;

public class InputJson {
	public List<GraphJson> graphs;

	@Override
	public String toString() {
		return String.format("%s", this.graphs);
	}
}
