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
	public int refund() {
		int result = (int)Math.floor(bidPrice / 2.0);
		money = money + result;
		return result;
	}

	public void purchase(int house) {
		ownHouse.add(house);
		pass = true;
	}

	public void clearBid() {
		pass = false;
		bidPrice = 0;
	}

	public void sell(Integer house, int price) {
		//house 必須用大 int，這樣在做 remove() 的時候才會是 remove 值而非 index
		ownHouse.remove(house);
		income += price;
	}

	public boolean isPass() {
		return pass;
	}
	public int getMoney() {
		return money;
	}
	public int getBidPrice() {
		return bidPrice;
	}
	public ArrayList<Integer> getOwnHouse() {
		return ownHouse;
	}
	/**
	 * 拍賣階段剩餘的金額、加上販賣階段的收入。
	 * <p>
	 * 遊戲結束時是以此作第一階段的排名依據。
	 */
	public int getProfit() {
		return money + income;
	}
}
