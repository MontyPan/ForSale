package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;

public class Player {
	public final String name;

	private boolean pass;

	/** 紀錄拍賣階段的金錢 */
	private int money;

	/** 紀錄賣房子獲得的金額總和 **/
	private int income;

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
	public int refund(boolean floorMode) {
		int result = (int)(floorMode ? Math.floor(bidPrice / 2.0) : Math.ceil(bidPrice / 2.0));
		money = money + result;
		return result;
	}

	public void purchase(int house) {
		ownHouse.add(house);
	}

	public void sell(Integer house, int price) {
		//house 必須用大 int，這樣在做 remove() 的時候才會是 remove 值而非 index
		ownHouse.remove(house);
		income += price;
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
	/**
	 * 拍賣階段剩餘的金額、加上販賣階段的收入。
	 * <p>
	 * 遊戲結束時是以此作第一階段的排名依據。
	 */
	public int getTotalMoney() {
		return money + income;
	}
}
