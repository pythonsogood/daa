package org.pythonsogood.serialization;

public class InputJson {
	public String text;
	public String pattern;

	public InputJson(String text, String pattern) {
		this.text = text;
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		return String.format("%s in %s", this.pattern, this.text);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof InputJson inputJson) {
			return inputJson.text.equals(this.text) && inputJson.pattern.equals(this.pattern);
		} else {
			return false;
		}
	}
}
