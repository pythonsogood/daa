package org.pythonsogood.interfaces;

import java.util.List;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;

public abstract class MSTAlgorithm {
	protected int operations = 0;

	public MSTAlgorithm() {}

	public int getOperations() {
		return this.operations;
	}

	public void resetOperations() {
		this.operations = 0;
	}

	public abstract List<Edge> mst(Graph graph);
}
