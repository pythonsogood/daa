package org.pythonsogood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClosestPair {
	public static class Pair {
		public Point a;
		public Point b;

		public Pair(Point a, Point b) {
			this.a = a;
			this.b = b;
		}

		public double distance() {
			return this.a.distanceTo(b);
		}

		@Override
		public String toString() {
			return String.format("(%s, %s) %s", this.a, this.b, this.distance());
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}

			if (!(o instanceof Pair)) {
				return false;
			}

			Pair pair = (Pair) o;

			return (pair.a.equals(this.a) && pair.b.equals(this.b)) || (pair.a.equals(this.b) && pair.b.equals(this.a));
		}
	}

	private static Pair recursive(Point[] points, Point[] points2, int left, int right) {
		if (right - left <= 3) {
			double shortest = Double.POSITIVE_INFINITY;
			Point a = null;
			Point b = null;

			for (int i=left; i<=right; i++) {
				for (int j=i+1; j<=right; j++) {
					double dist = points[i].distanceTo(points[j]);
					if (dist < shortest) {
						shortest = dist;
						a = points[i];
						b = points[j];
					}
				}
			}

			Pair best = a != null ? new Pair(a, b) : new Pair(points[left], points[left]);

			Arrays.sort(points, left, right + 1, (p1, p2) -> Double.compare(p1.y, p2.y));

			return best;
		}

		int mid = left + (right - left) / 2;

		Pair leftBest = recursive(points, points2, left, mid);
		Pair rightBest = recursive(points, points2, mid + 1, right);

		Pair best = (leftBest.distance() <= rightBest.distance()) ? leftBest : rightBest;
		double d = best.distance();

		// Merge sorted-by-y halves
		int i = left;
		int j = mid + 1;
		int k = left;

		while (i <= mid && j <= right) {
			if (points[i].y <= points[j].y) {
				points2[k++] = points[i++];
			} else {
				points2[k++] = points[j++];
			}
		}

		while (i <= mid) {
			points2[k++] = points[i++];
		}

		while (j <= right) {
			points2[k++] = points[j++];
		}

		for (k=left; k<=right; k++) {
			points[k] = points2[k];
		}

		int stripSize = 0;
		for (k=left; k<=right; k++) {
			if (Math.abs(points[k].x - points[mid].x) < d) {
				points2[stripSize++] = points[k];
			}
		}

		for (int p=0; p<stripSize; p++) {
			for (int q=p+1; q<stripSize && q<=p+7; q++) {
				if ((points2[q].y - points2[p].y) >= d) {
					break;
				}

				double dist = points2[p].distanceTo(points2[q]);
				if (dist < d) {
					d = dist;
					best = new Pair(points2[p], points2[q]);
				}
			}
		}

		return best;
	}

	public static Pair closest_points(List<Point> points) {
		List<Point> points_sorted_by_x = new ArrayList<>(points);
		points_sorted_by_x.sort((a, b) -> a.x - b.x);

		Point[] points_arr = points_sorted_by_x.stream().toArray(Point[]::new);
		Point[] points_arr2 = new Point[points_arr.length];

		return recursive(points_arr, points_arr2, 0, points_arr.length - 1);
	}
}
