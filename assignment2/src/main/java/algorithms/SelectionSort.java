package algorithms;

import java.util.List;

import metrics.PerformanceTracker;

import java.util.Arrays;

public class SelectionSort {
	private static void sort(int[] array) {
		int n = array.length;

		for (int i=0; i<n; i++) {
			int minIndex = i;

			for (int j=i + 1; j<n; j++) {
				PerformanceTracker.addComparison();

				PerformanceTracker.addArrayAccess();
				PerformanceTracker.addArrayAccess();

				if (array[j] < array[minIndex]) {
					minIndex = j;
				}
			}

			PerformanceTracker.addArrayAccess();
			PerformanceTracker.addArrayAccess();

			PerformanceTracker.addMemoryAllocation();

			int temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;
		}
	}

	public static List<Integer> sort(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		SelectionSort.sort(arr);

		return Arrays.stream(arr).boxed().toList();
	}
}
