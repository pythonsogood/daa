package algorithms;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SelectionSortTest {
	@Test
	@DisplayName("[5, 4, 3, 6, 1, 2] -> [1, 2, 3, 4, 5, 6]")
	public void test() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));

		int[] expected = {1, 2, 3, 4, 5, 6};

		int[] arraySorted = SelectionSort.sort(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[] -> []")
	public void testEmpty() {
		ArrayList<Integer> array = new ArrayList<>();

		int[] expected = {};

		int[] arraySorted = SelectionSort.sort(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[5] -> [5]")
	public void testSingle() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5));

		int[] expected = {5};

		int[] arraySorted = SelectionSort.sort(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[5, 1, 5, 4, 6, 7, 1] -> [1, 1, 4, 5, 5, 6, 7]")
	public void testDuplicates() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 1, 5, 4, 6, 7, 1));

		int[] expected = {1, 1, 4, 5, 5, 6, 7};

		int[] arraySorted = SelectionSort.sort(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[5, 4, 3, 6, 1, 2] -> [1, 2, 3, 4, 5, 6]")
	public void testOptimized() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));

		int[] expected = {1, 2, 3, 4, 5, 6};

		int[] arraySorted = SelectionSort.sortOptimized(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[] -> []")
	public void testEmptyOptimized() {
		ArrayList<Integer> array = new ArrayList<>();

		int[] expected = {};

		int[] arraySorted = SelectionSort.sortOptimized(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[5] -> [5]")
	public void testSingleOptimized() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5));

		int[] expected = {5};

		int[] arraySorted = SelectionSort.sortOptimized(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}

	@Test
	@DisplayName("[5, 1, 5, 4, 6, 7, 1] -> [1, 1, 4, 5, 5, 6, 7]")
	public void testDuplicatesOptimized() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 1, 5, 4, 6, 7, 1));

		int[] expected = {1, 1, 4, 5, 5, 6, 7};

		int[] arraySorted = SelectionSort.sortOptimized(array).stream().mapToInt(Integer::intValue).toArray();;

		Assertions.assertArrayEquals(arraySorted, expected);;
	}
}
