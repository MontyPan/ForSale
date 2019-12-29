package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;

public class Player {
	public final String name;

	private boolean pass;
	private int money;
	private int bidPrice;
	private int sellHouse;
	private ArrayList<Integer> ownHouse = new ArrayList<>();

	public Player(String name, int money) {
		this.name = name;
		this.money = money;
	}

	/** 喊價 */
	public void bid(int price) {
		money = money - price + bidPrice;
		bidPrice = price;
	}

	/** 非最高出價者，pass 時要退還喊價金額 */
	public void returnBid(boolean floorMode) {
		money = money + (int)(floorMode ? Math.floor(bidPrice / 2.0) : Math.ceil(bidPrice / 2.0));
	}

	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public int getMoney() {
		return money;
	}
	public int getBidPrice() {
		return bidPrice;
	}
	public int getSellHouse() {
		return sellHouse;
	}
	public void setSellHouse(int sellHouse) {
		this.sellHouse = sellHouse;
	}
	public ArrayList<Integer> getOwnHouse() {
		return ownHouse;
	}
}
