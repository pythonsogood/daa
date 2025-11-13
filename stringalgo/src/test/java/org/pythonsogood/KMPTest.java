package org.pythonsogood;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.pythonsogood.algorithm.KMP;

public class KMPTest {
	@Test
	public void testSmall() {
		List<Integer> matches = KMP.search("ababc", "ababcababc");

		assertEquals(Arrays.asList(0, 5), matches);
	}

	@Test
	public void testMedium() {
		List<Integer> matches = KMP.search("aaba", "aabaacaadaabaaba");

		assertEquals(Arrays.asList(0, 9, 12), matches);
	}

	@Test
	public void testLong() {
		List<Integer> matches = KMP.search("abcdabcy", "abcxabcdabxabcdabcdabcyabcdabcdabcyabcdabcxabcdabcdabcy");

		assertEquals(Arrays.asList(15, 27, 47), matches);
	}
}
