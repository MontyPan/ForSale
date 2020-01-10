package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameData {
	private static final int[] INIT_MONEY = {18, 18, 14, 14};
	private static final int CARD_AMOUNT = 30;

	/** pass 時是否返回向下取整的金額 */
	public final boolean floorMode;

	private final Player[] players;
	private final ArrayList<Integer> pool = new ArrayList<>();
	private final int[] allHouse = new int[CARD_AMOUNT];
	private final int[] allMoney = new int[CARD_AMOUNT];

	/** 紀錄 allHouse 出到第幾張 */
	private int houseIndex;

	/** 紀錄 allMoney 出到第幾張 */
	private int moneyIndex;

	private boolean bidMode = true;
	private int nowTurn;
	private int nowPlayer;
	private int nowPrice;

	public GameData(List<String> nameList) {
		this(nameList, true);
	}

	public GameData(List<String> nameList, boolean floorMode) {
		this.floorMode = floorMode;
		int playerAmount = nameList.size();
		players = new Player[playerAmount];

		for (int i = 0; i < nameList.size(); i++) {
			players[i] = new Player(nameList.get(i), getInitMoney());
		}

		prepare();
		newBidTurn();	//必須在 players[] init 之後才能執行
	}

	public int getPlayerAmount() {
		return players.length;
	}

	public int getTurnAmount() {
		return 11 - players.length;
	}

	public int getInitMoney() {
		return INIT_MONEY[players.length - 3];
	}

	public int getNowTurn() {
		return nowTurn;
	}

	public String getNowPlayer() {
		return players[nowPlayer].name;
	}

	public int getNowPrice() {
		return nowPrice;
	}

	/**
	 * 如果只是要取得 pool 剩餘牌數，請用改用效率較佳的 {@link #getPoolSize()}
	 */
	public int[] getPool() {
		int[] result = new int[pool.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = pool.get(i);
		}

		return result;
	}
	
	public int getPoolSize() {
		return pool.size();
	}

	public boolean isBidlMode() {
		return bidMode;
	}

	/**
	 * <b>注意</b>：會改變 {@link #nowPlayer} 與 {@link #pool} 的值
	 */
	public BidRecord bid(int price) {
		Player player = players[nowPlayer];

		if (price - player.getBidPrice() > player.getMoney()) { price = 0; }
		if (price < nowPrice + 1) { price = 0; }

		nowPlayer = nextBidPlayer();

		if (price >= nowPrice + 1) {
			player.bid(price);
			nowPrice = price;
			return new BidRecord(player.name, nowPrice, 0, 0);
		}

		//會到這邊就是 pass 的狀況
		//先拿走 pool 的房子
		int house = pool.remove(0);
		player.purchase(house);

		//最後一個 pass（該拍賣回合結束）不會退錢
		int refund = getPoolSize() == 0 ? 0 : player.refund(floorMode);

		//這裡也是觸發下一個回合的時機點，為了簡潔起見在這裡處理
		if (getPoolSize() == 0) {
			if (nowTurn < getTurnAmount()) {
				newBidTurn();
			} else {
				bidMode = false;
				newSellTurn();
			}
		}

		return new BidRecord(player.name, price, house, refund);
	}

	private int nextBidPlayer() {
		for (int i = 1; i < players.length; i++) {
			if (!players[(nowPlayer + i) % players.length].isPass()) {
				return (nowPlayer + i) % players.length;
			}
		}

		return nowPlayer;
	}

	private void prepare() {
		for (int i = 0; i < CARD_AMOUNT; i++) {
			allHouse[i] = i + 1;
		}

		Util.shuffle(allHouse);

		for (int i = 1; i < CARD_AMOUNT / 2; i++) {
			allMoney[i * 2] = i + 1;
			allMoney[i * 2 + 1] = i + 1;
		}

		Util.shuffle(allMoney);
	}

	private void newBidTurn() {
		nowTurn++;
		nowPrice = 0;

		for (int i = 0; i < players.length; i++) {
			pool.add(allHouse[houseIndex]);
			houseIndex++;
		}

		Collections.sort(pool);

		for (Player player : players) {
			player.clearBid();
		}
	}

	private void newSellTurn() {
		//TODO
	}
}
