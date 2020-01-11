package us.dontcareabout.forSale.client.model;

import java.util.Random;

public class Util {
	private static final int CARD_AMOUNT = 30;
	private static final Random random = new Random();

	private static final int[] HOUSE_DECK = new int[CARD_AMOUNT];
	private static final int[] MONEY_DECK = new int[CARD_AMOUNT];
	static {
		for (int i = 0; i < CARD_AMOUNT; i++) {
			HOUSE_DECK[i] = i + 1;
		}
		for (int i = 1; i < CARD_AMOUNT / 2; i++) {
			MONEY_DECK[i * 2] = i + 1;
			MONEY_DECK[i * 2 + 1] = i + 1;
		}
	}

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

	public static int[] genHouseDeck() {
		int[] result = HOUSE_DECK.clone();
		shuffle(result);
		return result;
	}

	public static int[] genMoneyDeck() {
		int[] result = MONEY_DECK.clone();
		shuffle(result);
		return result;
	}
}
