package org.pythonsogood.algorithm;

import java.util.ArrayList;
import java.util.List;

public class KMP {
	public static List<Integer> search(String pattern, String text) {
		List<Integer> matches = new ArrayList<>();

		int n = text.length();
		int m = pattern.length();

		int[] lps = LPS.constructArray(pattern);

		// traversing pointers
		int i = 0;
		int j = 0;

		while (i < n) {
			if (text.charAt(i) == pattern.charAt(j)) {
				// chars match
				// move pointers forward
				i++;
				j++;

				if (j == m) {
					// end of pattern, add match
					matches.add(i - j);
					j = lps[j - 1];
				}
			} else if (j != 0) {
				// j is not at start of pattern
				// use previous LPS value, move j back
				j = lps[j - 1];
			} else {
				// j is at start of pattern
				// move i forward
				i++;
			}
		}

		return matches;
	}
}
