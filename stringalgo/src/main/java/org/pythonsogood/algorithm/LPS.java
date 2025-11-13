package org.pythonsogood.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LPS {
	public static int[] constructArray(String pattern) {
		int n = pattern.length();

		int[] lps = new int[n];

		Arrays.fill(lps, 0);

		int len = 0;
		int i = 1;

		while (i < len) {
			if (pattern.charAt(i) == pattern.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else if (len != 0) {
				len = lps[len - 1];
			} else {
				lps[i] = 0;
				i++;
			}
		}

		return lps;
	}

	public static List<Integer> constructList(String pattern) {
		List<Integer> lps = new ArrayList<>();

		int n = pattern.length();

		for (int i=0; i<n; i++) {
			lps.add(0);
		}

		int len = 0;
		int i = 1;

		while (i < len) {
			if (pattern.charAt(i) == pattern.charAt(len)) {
				len++;
				lps.set(i, len);
				i++;
			} else if (len != 0) {
				len = lps.get(len - 1);
			} else {
				lps.set(i, 0);
				i++;
			}
		}

		return lps;
	}
}
