package algorithms;

import java.util.List;

import metrics.PerformanceTracker;

import java.util.Arrays;

public class SelectionSort {
	private static void sort(int[] array) {
		int n = array.length;

		PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

		for (int i=0; i<n; i++) {
			int minIndex = i;

			PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

			for (int j=i+1; j<n; j++) {
				if (array[j] < array[minIndex]) {
					minIndex = j;
				}

				PerformanceTracker.addComparison();

				PerformanceTracker.addArrayAccess();
				PerformanceTracker.addArrayAccess();
			}

			int temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;

			PerformanceTracker.addArrayAccess();

			PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

			PerformanceTracker.addArrayAccess();

			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
		}

		PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
	}

	private static void sortOptimized(int[] array) {
		int n = array.length;

		PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

		boolean sorted = true;

		PerformanceTracker.addMemoryAllocation(PerformanceTracker.BOOLEAN_SIZE);

		for (int i=1; i<n; i++) {
			if (array[i - 1] > array[i]) {
				sorted = false;
				break;
			}
		}

		if (sorted) {
			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.BOOLEAN_SIZE);
			return;
		}

		for (int i=0; i<n; i++) {
			int minIndex = i;

			PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

			for (int j=i+1; j<n; j++) {
				if (array[j] < array[minIndex]) {
					minIndex = j;
				}

				PerformanceTracker.addComparison();

				PerformanceTracker.addArrayAccess();
				PerformanceTracker.addArrayAccess();
			}

			int temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;

			PerformanceTracker.addArrayAccess();

			PerformanceTracker.addMemoryAllocation(PerformanceTracker.INT_SIZE);

			PerformanceTracker.addArrayAccess();

			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
			PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
		}

		PerformanceTracker.removeMemoryAllocation(PerformanceTracker.INT_SIZE);
		PerformanceTracker.removeMemoryAllocation(PerformanceTracker.BOOLEAN_SIZE);
	}

	public static List<Integer> sort(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		SelectionSort.sort(arr);

		return Arrays.stream(arr).boxed().toList();
	}

	public static List<Integer> sortOptimized(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		SelectionSort.sortOptimized(arr);

		return Arrays.stream(arr).boxed().toList();
	}
}
