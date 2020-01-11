package us.dontcareabout.forSale.client.model.ai;

import java.util.Collections;
import java.util.List;

import us.dontcareabout.forSale.client.model.BidRecord;
import us.dontcareabout.forSale.client.model.Player;
import us.dontcareabout.forSale.client.model.SellRecord;

/**
 * 喊價最多喊到 6。
 * 賣房子時候若第二大的金額超過 10 就出第二大的房子，
 * 反之就出第二小的房子。
 */
public class Newbie implements AI {
	private Player me;
	private int turnAmount;

	@Override
	public void gameInit(String myName, int playerAmount, int turnAmount, int initMoney) {
		me = new Player(myName, initMoney);
		this.turnAmount = turnAmount;
	}

	@Override
	public void newBidTurn(int turnNumber, int[] pool) {}

	@Override
	public int bid(int[] pool, int nowPrice) {
		return nowPrice > 5 ? 0 : nowPrice + 1;
	}

	@Override
	public void bidCourse(BidRecord record) {
		if (record.player.equals(me.name)) {
			if (record.isPass()) {
				me.purchase(record.house);
				me.refund();
			} else {
				me.bid(record.price);
			}
		}

		if (me.getOwnHouse().size() == turnAmount) { Collections.sort(me.getOwnHouse()); }
	}

	@Override
	public int sell(int[] pool) {
		List<Integer> house = me.getOwnHouse();

		if (pool[pool.length - 2] > 10) {
			return house.get(house.size() - (house.size() > 2 ? 2 :1));
		} else {
			return me.getOwnHouse().get(me.getOwnHouse().size() > 1 ? 1 : 0);
		}
	}

	@Override
	public void sellResult(SellRecord[] records) {
		for (SellRecord sr : records) {
			if (sr.player.equals(me.name)) {
				me.sell(sr.house, sr.money);
			}
		}
	}
}
