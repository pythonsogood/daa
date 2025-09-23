package org.pythonsogood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MergeSortTest {
	@Test
	@DisplayName("[5, 4, 3, 6, 1, 2] -> [1, 2, 3, 4, 5, 6]")
	public void test() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));

		int[] expected = {1, 2, 3, 4, 5, 6};

		int[] arraySorted = MergeSort.sort(array).stream().mapToInt(Integer::intValue).toArray();

		Assertions.assertArrayEquals(arraySorted, expected);
	}

	@Test
	@DisplayName("Random")
	public void testRandom() {
		Random random = new Random();

		ArrayList<Integer> array = new ArrayList<>();

		for (int i=0; i<10; i++) {
			array.add(random.nextInt(10));
		}

		int[] expected = array.stream().mapToInt(Integer::intValue).sorted().toArray();

		int[] arraySorted = MergeSort.sort(array).stream().mapToInt(Integer::intValue).toArray();

		Assertions.assertArrayEquals(arraySorted, expected);
	}
}
