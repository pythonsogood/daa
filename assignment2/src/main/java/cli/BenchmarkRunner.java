package cli;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import algorithms.Main;
import algorithms.SelectionSort;

public class BenchmarkRunner {
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

	public static void main(String[] args) throws IOException {
		int[] sizes = {100, 1000, 10000, 100000};

		FileWriter writer = new FileWriter("metrics.csv");

		writer.write("size,time,comparisons,swaps,array_accesses,memory_allocations\n");

		for (int n : sizes) {
			Main.metricsReset();

			List<Integer> array = createRandomIntArray(n);

			long startTime = System.nanoTime();

			SelectionSort.sort(array);

			long endTime = System.nanoTime();

			writer.write(String.format("%s,%s,%s,%s,%s,%s\n", n, endTime - startTime, Main.getMetric(0), Main.getMetric(1), Main.getMetric(2), Main.getMetric(3)));
		}

		writer.close();
    }
}
