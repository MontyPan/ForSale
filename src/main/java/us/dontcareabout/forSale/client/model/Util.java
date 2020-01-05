package us.dontcareabout.forSale.client.model;

import java.util.Random;

public class Util {
	private static final Random random = new Random();

	public static void shuffle(int[] array) {
		int last = array.length - 1;
		int target;

		while(last > 0) {
			target = random.nextInt(last + 1);
			int tmp = array[last];
			array[last] = array[target];
			array[target] = tmp;
			last--;
		}
	}
}
