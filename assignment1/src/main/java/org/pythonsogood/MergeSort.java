package org.pythonsogood;

import java.util.Arrays;
import java.util.List;

public class MergeSort {
	private static void merge(int[] array, int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;

		int[] L = new int[n1];
		int[] R = new int[n2];

		for (int i=0; i<n1; i++) {
			L[i] = array[left + i];
		}

		for (int i=0; i<n2; i++) {
			R[i] = array[mid + 1 + i];
		}

		int i = 0;
		int j = 0;
		int k = left;

		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				array[k] = L[i];
				i++;
			} else {
				array[k] = R[j];
				j++;
			}

			k++;
		}

		while (i < n1) {
			array[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			array[k] = R[j];
			j++;
			k++;
		}
	}

	private static void merge_sort(int[] array, int left, int right) {
		if (left >= right) {
			return;
		}

		int mid = (left + right) / 2;
		merge_sort(array, left, mid);
		merge_sort(array, mid + 1, right);
		merge(array, left, mid, right);
	}

	public static List<Integer> sort(List<Integer> array) {
		int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
		merge_sort(arr, 0, array.size() - 1);

		return Arrays.stream(arr).boxed().toList();
	}
}