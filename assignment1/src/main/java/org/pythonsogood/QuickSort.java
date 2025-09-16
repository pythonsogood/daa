package org.pythonsogood;

import java.util.Arrays;
import java.util.List;

public class QuickSort {
	private static int partition(int[] array, int left, int right) {
		int pivot = array[right];

		int i = left - 1;

		for (int j=left; j<right; j++) {
			if (array[j] < pivot) {
				i++;

				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}

		int temp = array[i + 1];
		array[i + 1] = array[right];
		array[right] = temp;

		return i + 1;
	}

	private static void quick_sort(int[] array, int left, int right) {
		if (left >= right) {
			return;
		}

		int pivot = partition(array, left, right);

		quick_sort(array, left, pivot - 1);
		quick_sort(array, pivot + 1, right);
	}

	public static List<Integer> sort(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		quick_sort(arr, 0, array.size() - 1);

		return Arrays.stream(arr).boxed().toList();
	}
}
