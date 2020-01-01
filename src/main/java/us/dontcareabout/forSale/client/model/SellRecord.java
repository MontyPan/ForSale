package us.dontcareabout.forSale.client.model;

public class SellRecord {
	public final String player;

	public final int house;

	public final int money;

	public SellRecord(String player, int house, int money) {
		this.player = player;
		this.house = house;
		this.money = money;
	}
}
