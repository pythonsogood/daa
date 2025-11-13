package org.pythonsogood.algorithm;

import java.util.Arrays;

public class LPS {
	public static int[] constructArray(String pattern) {
		int n = pattern.length();

		int[] lps = new int[n];

		Arrays.fill(lps, 0);

		int len = 0;
		int i = 1;

		while (i < n) {
			if (pattern.charAt(i) == pattern.charAt(len)) {
				// current char continues existing prefix-suffix match
				len++;
				lps[i] = len;
				i++;
			} else if (len != 0) {
				// previous prefix-suffix cannot be extended
				// reuse previous LPS to try find shorter prefix-suffix
				len = lps[len - 1];
			} else {
				// no prefix with matching any suffix at i
				lps[i] = 0;
				i++;
			}
		}

		return lps;
	}
}
