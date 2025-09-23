package org.pythonsogood;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeterministicSelectTest {
	@Test
	@DisplayName("[5, 4, 3, 6, 1, 2] -> 3.5")
	public void test() {
		ArrayList<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));

		double expected = 3.5;

		double median = DeterministicSelect.select(array);

		Assertions.assertEquals(median, expected);
	}
}
