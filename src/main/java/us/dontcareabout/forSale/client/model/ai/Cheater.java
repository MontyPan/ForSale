package us.dontcareabout.forSale.client.model.ai;

import java.util.Arrays;

import us.dontcareabout.forSale.client.model.BidRecord;
import us.dontcareabout.forSale.client.model.SellRecord;

/**
 * 努力犯規作弊搗亂的 AI
 */
public class Cheater implements AI {
	private int money;

	@Override
	public void gameInit(String myName, int playerAmount, int turnAmount, int initMoney) {
		money = initMoney;
	}

	@Override
	public void newBidTurn(int turnNumber, int[] pool) {
		pool[0] = Integer.MAX_VALUE;
	}

	@Override
	public int bid(int[] pool, int nowPrice) {
		pool[0] = Integer.MAX_VALUE;
		return money / 2 < nowPrice ? nowPrice : money / 2;
	}

	@Override
	public void bidCourse(BidRecord record) {}

	@Override
	public int sell(int[] pool) {
		pool[0] = Integer.MAX_VALUE;
		return 30;
	}

	@Override
	public void sellResult(SellRecord[] records) {
		Arrays.fill(records, null);
	}
}
