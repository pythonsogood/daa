package cli;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import algorithms.SelectionSort;
import metrics.PerformanceTracker;

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

		writer.write("algorithm,n,time_ns,comparisons,swaps,array_accesses,memory_allocations\n");

		for (int n : sizes) {
			PerformanceTracker.reset();

			List<Integer> array = createRandomIntArray(n);

			long startTime = System.nanoTime();

			SelectionSort.sort(array);

			long endTime = System.nanoTime();

			writer.write(String.format("SelectionSort,%s,%s,%s,%s,%s,%s\n", n, endTime - startTime, PerformanceTracker.getComparisons(), PerformanceTracker.getSwaps(), PerformanceTracker.getArrayAccesses(), PerformanceTracker.getMaxMemoryAllocations()));

			PerformanceTracker.reset();

			startTime = System.nanoTime();

			SelectionSort.sortOptimized(array);

			endTime = System.nanoTime();

			writer.write(String.format("SelectionSort (optimized),%s,%s,%s,%s,%s,%s\n", n, endTime - startTime, PerformanceTracker.getComparisons(), PerformanceTracker.getSwaps(), PerformanceTracker.getArrayAccesses(), PerformanceTracker.getMaxMemoryAllocations()));

			PerformanceTracker.reset();

			array.sort(null);

			startTime = System.nanoTime();

			SelectionSort.sort(array);

			endTime = System.nanoTime();

			writer.write(String.format("SelectionSort best case,%s,%s,%s,%s,%s,%s\n", n, endTime - startTime, PerformanceTracker.getComparisons(), PerformanceTracker.getSwaps(), PerformanceTracker.getArrayAccesses(), PerformanceTracker.getMaxMemoryAllocations()));

			PerformanceTracker.reset();

			startTime = System.nanoTime();

			SelectionSort.sortOptimized(array);

			endTime = System.nanoTime();

			writer.write(String.format("SelectionSort (optimized) best case,%s,%s,%s,%s,%s,%s\n", n, endTime - startTime, PerformanceTracker.getComparisons(), PerformanceTracker.getSwaps(), PerformanceTracker.getArrayAccesses(), PerformanceTracker.getMaxMemoryAllocations()));
		}

		writer.close();
    }
}
