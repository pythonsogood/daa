package org.pythonsogood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	private static List<Integer> merge(List<Integer> left, List<Integer> right) {
		ArrayList<Integer> merged = new ArrayList<>();
		int i = 0;
		int j = 0;

		while (i < left.size() && j < right.size()) {
			if (left.get(i) <= right.get(j)) {
				merged.add(left.get(i));
				i++;
			} else {
				merged.add(right.get(j));
				j++;
			}
		}


		merged.addAll(left.subList(i, left.size()));
		merged.addAll(right.subList(j, right.size()));

		return merged;
	}

	public static List<Integer> merge_sort(List<Integer> array) {
		if (array.size() <= 1) {
			return array;
		}

		int mid = array.size() / 2;
		List<Integer> left = array.subList(0, mid);
		List<Integer> right = array.subList(mid, array.size());

		return merge(merge_sort(left), merge_sort(right));
	}

    public static void main(String[] args) {
        Random random = new Random();
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			array.add(random.nextInt(10));
		}

		System.out.println(array);
		System.out.println(merge_sort(array));
    }
}