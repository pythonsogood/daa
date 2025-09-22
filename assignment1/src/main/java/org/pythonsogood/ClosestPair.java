package org.pythonsogood;

import java.util.ArrayList;
import java.util.List;

public class ClosestPair {
	public static List<Point> closest_points(List<Point> points) {
		List<Point> points_sorted_by_x = new ArrayList<>(points);
		points_sorted_by_x.sort((a, b) -> a.x - b.x);

		return null;
	}
}
