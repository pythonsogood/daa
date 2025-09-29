package org.pythonsogood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	// {Comparisons, swaps, array accesses, memory allocations}
	private static long[] metrics = {0, 0, 0, 0};

	public static void metricEnter(int x) {
		Main.metrics[x]++;
	}

	public static void metricsReset() {
		for (int x=0; x<4; x++) {
			Main.metrics[x] = 0;
		}
	}

	public static long getMetric(int x) {
		return Main.metrics[x];
	}

	public static ArrayList<Integer> createRandomIntArray(int min, int max, int size) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		Random random = new Random();

		for (int i=0; i<size; i++) {
			array.add(i, random.nextInt(min, max + 1));
		}

		return array;
	}

	public static ArrayList<Integer> createRandomIntArray(int max, int size) {
		return createRandomIntArray(0, max, size);
	}

	public static ArrayList<Integer> createRandomIntArray(int size) {
		return createRandomIntArray(0, 100, size);
	}

    public static void main(String[] args) {
		for (int n=0; n<1000; n++) {
			Main.metricsReset();

			List<Integer> array = createRandomIntArray(n);

			long startTime = System.nanoTime();

			List<Integer> sortedArray = SelectionSort.sort(array);

			long endTime = System.nanoTime();

			System.out.println(String.format("%s | %s | %s | %s | %s | %s", n, endTime - startTime, Main.getMetric(0), Main.getMetric(1), Main.getMetric(2), Main.getMetric(3)));
		}
    }
}