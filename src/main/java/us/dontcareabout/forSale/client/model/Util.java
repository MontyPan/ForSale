package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;
import java.util.List;
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

	public static String toString(BidRecord br) {
		StringBuilder result = new StringBuilder(br.player + "\t");

		if (br.isPass()) {
			result.append("pass，得到 " + br.house + "，取回 " + br.returnMoney);
		} else {
			result.append("出價 " + br.price);
		}

		return result.toString();
	}

	public static String toString(SellRecord sr) {
		StringBuilder builder = new StringBuilder()
			.append(sr.player).append("\t賣出 ").append(sr.house)
			.append("\t得到 ").append(sr.money);
		return builder.toString();
	}

	public static String toString(GameResult gr) {
		return gr.name + "\t" + gr.profit + " (" + gr.balance + ")";
	}

	// ======== 從 openhome.cc 幹來的 permutation 寫法 ======== //
	public static <T> List<List<T>> permutation(List<T> list) {
		List<List<T>> result = new ArrayList<>();

		if(list.isEmpty()) {
			result.add(new ArrayList<T>());
			return result;
		}

		for (List<T> lt : allRotated(list)) {
			for (List<T> tailPl : permutation(lt.subList(1, lt.size()))) {
				List<T> pl = new ArrayList<>();
				pl.add(lt.get(0));
				pl.addAll(tailPl);
				result.add(pl);
			}
		}

		return result;
	}

	private static <T> List<T> rotatedTo(int i, List<T> list) {
		List<T> result = new ArrayList<>();
		result.add(list.get(i));
		result.addAll(list.subList(0, i));
		result.addAll(list.subList(i + 1, list.size()));
		return result;
	}

	private static <T> List<List<T>> allRotated(List<T> list) {
		List<List<T>> result = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			result.add(rotatedTo(i, list));
		}

		return result;
	}
	// ================ //
}
