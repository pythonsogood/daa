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
}
