package us.dontcareabout.forSale.client.model.ai;

import us.dontcareabout.forSale.client.model.BidRecord;
import us.dontcareabout.forSale.client.model.SellRecord;

/**
 * 拍賣階段只會喊同一個值（在 constructor 可設定），販賣階段永遠都是賣不存在於遊戲中的房子 0。
 */
public class Dummy implements AI {
	private final int dummyBid;

	public Dummy() {
		this(2);
	}

	public Dummy(int dummyBid) {
		this.dummyBid = dummyBid;
	}

	@Override
	public void gameInit(String myName, int playerAmount, int turnAmount, int initMoney) {}

	@Override
	public void newBidTurn(int turnNumber, int[] pool) {}

	@Override
	public int bid(int[] pool, int nowPrice) {
		return dummyBid;
	}

	@Override
	public void bidCourse(BidRecord record) {}

	@Override
	public int sell(int[] pool) {
		return 0;
	}

	@Override
	public void sellResult(SellRecord[] records) {}
}
