package org.pythonsogood;

import java.util.List;

// https://en.wikipedia.org/wiki/Median_of_medians
public class DeterministicSelect {
	private static int median(int[] array) {
		if (array.length % 2 != 0) {
			return nthSmallest(array, array.length / 2);
		}

		return (nthSmallest(array, array.length / 2 - 1) + nthSmallest(array, array.length / 2)) / 2;
	}

	private static int nthSmallest(int[] array, int n) {
		return array[select(array, 1, array.length, n)];
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
			} else if (n < pivotIndex) {
				right = pivotIndex - 1;
			} else {
				left = pivotIndex + 1;
			}
		}
	}

	private static int pivot(int[] array, int left, int right) {
		if ((right - left) < 5) {
			return partition5(array, left, right);
		}

		for (int i=left; i<=right; i+=5) {
			int subRight = i + 4;
			if (subRight > right) {
				subRight = right;
			}

			int median5 = partition5(array, i, subRight);

			int median5Value = array[median5];
			int tempIndex = left + (int) Math.floor((double) (i - left) / 5);
			array[median5] = array[tempIndex];
			array[tempIndex] = median5Value;
		}

		int mid = (int) Math.floor((double) (((right - left) / 10) + left + 1));

		return select(array, left, left + (int) Math.floor((double) ((right - left) / 5)), mid);
	}

	private static int partition(int[] array, int left, int right, int pivotIndex, int n) {
		int pivot = array[pivotIndex];

		array[pivotIndex] = array[right];
		array[right] = pivot;

		int storeIndex = left;

		for (int i=left; i<right; i++) {
			if (array[i] < pivot) {
				int store = array[storeIndex];
				array[storeIndex] = array[i];
				array[i] = store;

				storeIndex++;
			}
		}

		int storeIndexEq = storeIndex;
		for (int i=storeIndex; i<right; i++) {
			if (array[i] == pivot) {
				int storeValue = array[storeIndex];
				array[storeIndex] = array[i];
				array[i] = storeValue;

				storeIndexEq++;
			}
		}

		int rightValue = array[right];
		array[right] = array[storeIndexEq];
		array[storeIndexEq] = rightValue;

		if (n < storeIndex) {
			return storeIndex;
		}

		if (n <= storeIndexEq) {
			return n;
		}

		return storeIndexEq;
	}

	private static int partition5(int[] array, int left, int right) {
		int i = left + 1;

		while (i <= right) {
			int j = i;

			while (j > left && array[j-1] > array[j]) {
				int temp = array[j-1];
				array[j-1] = array[j];
				array[j] = temp;

				j--;
			}

			i++;
		}

		return left + (right - left) / 2;
	}

	public static Integer select(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		return median(arr);
	}
}
