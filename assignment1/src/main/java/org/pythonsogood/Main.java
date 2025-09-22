package org.pythonsogood;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			array.add(random.nextInt(10));
		}

		System.out.println(array);
		System.out.println(DeterministicSelect.select(array));
    }
}