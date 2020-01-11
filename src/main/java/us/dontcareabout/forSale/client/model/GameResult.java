package us.dontcareabout.forSale.client.model;

public class GameResult implements Comparable<GameResult> {
	public final String name;

	/** 結束時的總金額 */
	public final int profit;
	/** 起始金錢的剩餘金額 */
	public final int balance;

	public GameResult(String name, int profit, int balance) {
		this.name = name;
		this.profit = profit;
		this.balance = balance;
	}

	@Override
	public int compareTo(GameResult o) {
		//跟慣例相反，數字越大再越前面
		if (profit == o.profit) {
			return o.balance - balance;
		} else {
			return o.profit - profit;
		}
	}
}
