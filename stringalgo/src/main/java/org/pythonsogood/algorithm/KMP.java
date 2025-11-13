package org.pythonsogood.algorithm;

import java.util.ArrayList;
import java.util.List;

public class KMP {
	public static List<Integer> search(String pattern, String text) {
		List<Integer> matches = new ArrayList<>();

		int n = text.length();
		int m = pattern.length();

		int[] lps = LPS.constructArray(pattern);

		int i = 0;
		int j = 0;

		while (i < n) {
			if (text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;

				if (j == m) {
					matches.add(i - j);
					j = lps[j - 1];
				}
			} else if (j != 0) {
				j = lps[j - 1];
			} else {
				i++;
			}
		}

		return matches;
	}
}
