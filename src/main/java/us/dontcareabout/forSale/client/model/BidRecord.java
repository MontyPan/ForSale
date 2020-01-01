package us.dontcareabout.forSale.client.model;

public class BidRecord {
	public final String player;

	/** 0 表示該次行動選擇 pass */
	public final int price;

	/** 該次行動選擇 pass 而得到的房屋編號 */
	public final int house;

	/** 該次行動選擇 pass 而退回的金額 */
	public final int returnMoney;

	public BidRecord(String player, int bidPrice, int house, int returnMoney) {
		this.player = player;
		this.price = bidPrice;
		this.house = house;
		this.returnMoney = returnMoney;
	}

	public boolean isPass() {
		return price == 0;
	}
}
