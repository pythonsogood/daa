package org.pythonsogood;

import java.util.List;

// https://en.wikipedia.org/wiki/Median_of_medians
public class DeterministicSelect {
	private static double median(int[] array) {
		if (array.length % 2 != 0) {
			return nthSmallest(array, array.length / 2);
		}

		return (nthSmallest(array, array.length / 2 - 1) + nthSmallest(array, array.length / 2)) / 2.0;
	}

	private static int nthSmallest(int[] array, int n) {
		return array[select(array, 0, array.length - 1, n)];
	}

	private static int select(int[] array, int left, int right, int n) {
		while (true) {
			if (left == right) {
				return left;
			}

			int pivotIndex = pivot(array, left, right);
			pivotIndex = partition(array, left, right, pivotIndex, n);

			if (n == pivotIndex) {
				return n;
			}  else if (n < pivotIndex) {
				right = pivotIndex - 1;
			} else {
				left = pivotIndex + 1;
			}
		}
	}

	private static int pivot(int[] array, int left, int right) {
		if (right - left < 5) {
			return partition5(array, left, right);
		}

		int medians = 0;
		for (int i=left; i<=right; i+=5) {
			int subRight = Math.min(i + 4, right);
			int median5 = partition5(array, i, subRight);

			int temp = array[left + medians];
			array[left + medians] = array[median5];
			array[median5] = temp;

			medians++;
		}

		int mid = left + (medians - 1) / 2;

		return select(array, left, left + medians - 1, mid);
	}

	private static int partition(int[] array, int left, int right, int pivotIndex, int n) {
		int pivot = array[pivotIndex];

		array[pivotIndex] = array[right];
		array[right] = pivot;

		int storeIndex = left;

		for (int i=left; i<right; i++) {
			if (array[i] < pivot) {
				int temp = array[storeIndex];
				array[storeIndex] = array[i];
				array[i] = temp;

				storeIndex++;
			}
		}

		int storeIndexEq = storeIndex;
		for (int i=storeIndex; i<right; i++) {
			if (array[i] == pivot) {
				int temp = array[storeIndexEq];
				array[storeIndexEq] = array[i];
				array[i] = temp;

				storeIndexEq++;
			}
		}

		int temp = array[storeIndexEq];
		array[storeIndexEq] = array[right];
		array[right] = temp;

		if (n < storeIndex) {
			return storeIndex;
		}

		if (n <= storeIndexEq) {
			return n;
		}

		return storeIndexEq;
	}

	private static int partition5(int[] array, int left, int right) {
		for (int i=left + 1; i<=right; i++) {
			int j = i;
			while (j > left && array[j - 1] > array[j]) {
				int temp = array[j - 1];
				array[j - 1] = array[j];
				array[j] = temp;

				j--;
			}
		}

		return left + (right - left) / 2;
	}

	public static Double select(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		return median(arr);
	}
}
