package metrics;

import java.lang.instrument.Instrumentation;

public class PerformanceTracker {
	private static Instrumentation instrumentation = new Instrumentation() {};

	private static int comparisons = 0;
	private static int swaps = 0;
	private static int arrayAccesses = 0;
	private static long memoryAllocations = 0;

	public static void addComparison() {
		PerformanceTracker.comparisons++;
	}

	public static void addSwap() {
		PerformanceTracker.swaps++;
	}

	public static void addArrayAccess() {
		PerformanceTracker.arrayAccesses++;
	}

	public static long addMemoryAllocation(Object object) {
		PerformanceTracker.memoryAllocations += size;
	}

	public static void removeMemoryAllocation(long size) {
		PerformanceTracker.memoryAllocations -= size;
	}

	public static void reset() {
		PerformanceTracker.comparisons = 0;
		PerformanceTracker.swaps = 0;
		PerformanceTracker.arrayAccesses = 0;
		PerformanceTracker.memoryAllocations = 0;
	}

	public static int getComparisons() {
		return PerformanceTracker.comparisons;
	}

	public static int getSwaps() {
		return PerformanceTracker.swaps;
	}

	public static int getArrayAccesses() {
		return PerformanceTracker.arrayAccesses;
	}

	public static long getMemoryAllocations() {
		return PerformanceTracker.memoryAllocations;
	}
}
