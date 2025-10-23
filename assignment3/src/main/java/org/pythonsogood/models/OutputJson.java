package org.pythonsogood.models;

import java.util.List;

public class OutputJson {
	public List<OutputResultJson> results;

	public OutputJson(List<OutputResultJson> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return String.format("%s", this.results);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputJson outputJson) {
			return outputJson.results.equals(this.results);
		} else {
			return false;
		}
	}

	public boolean equals(Object o, boolean onlyAlgorithm) {
		if (o instanceof OutputJson outputJson) {
			if (this.results.size() != outputJson.results.size()) {
				return false;
			}

			for (int i=0; i<this.results.size(); i++) {
				if (!this.results.get(i).equals(outputJson.results.get(i), onlyAlgorithm)) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
