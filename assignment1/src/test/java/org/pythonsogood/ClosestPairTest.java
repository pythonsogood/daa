package org.pythonsogood;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClosestPairTest {
	@Test
	@DisplayName("[(0, 0), (1, 5), (2, 7), (8, 3)] -> ((1, 5), (2.7), distance):")
	public void test() {
		ArrayList<Point> array = new ArrayList<>(Arrays.asList(
			new Point(0, 0),
			new Point(1, 5),
			new Point(2, 7),
			new Point(8, 3)
		));

		ClosestPair.Pair expected = new ClosestPair.Pair(new Point(1, 5), new Point(2, 7));

		ClosestPair.Pair pair = ClosestPair.closest_points(array);

		Assertions.assertEquals(pair, expected);
	}
}
