package org.pythonsogood.serialization;

import java.util.List;

public class OutputJson {
	public String text;
	public String pattern;
	public List<Integer> matches;

	public OutputJson(String text, String pattern, List<Integer> matches) {
		this.text = text;
		this.pattern = pattern;
		this.matches = matches;
	}

	@Override
	public String toString() {
		return String.format("%s %s in %s", this.matches, this.pattern, this.text);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputJson outputJson) {
			return outputJson.text.equals(this.text) && outputJson.pattern.equals(this.pattern) && outputJson.matches.equals(this.matches);
		} else {
			return false;
		}
	}
}
