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
		prepare();
		newBidTurn();

		//TODO 亂數位置
		for (int i = 0; i < nameList.size(); i++) {
			players[i] = new Player(nameList.get(i), getInitMoney());
		}
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

	public int[] getPool() {
		int[] result = new int[pool.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = pool.get(i);
		}

		return result;
	}

	public boolean isBidlMode() {
		return bidMode;
	}

	public boolean isBidEnd() {
		return pool.size() == 0;
	}

	public BidRecord bid(int price) {
		Player player = players[nowPlayer];

		if (price - player.getBidPrice() > player.getMoney()) { price = 0; }
		if (price < nowPrice + 1) { price = 0; }

		if (price >= nowPrice + 1) {
			player.bid(price);
			nowPrice = price;
			nowPlayer = (nowPlayer + 1) % players.length;
			return new BidRecord(player.name, nowPrice, 0, 0);
		}

		//會到這邊就是 pass 的狀況
		int house = pool.remove(0);
		player.purchase(house);

		//最後一個 pass（該拍賣回合結束）不會退錢
		int refund = isBidEnd() ? 0 : player.refund(floorMode);

		//這裡也是觸發下一個回合的時機點，為了簡潔起見在這裡處理
		//TODO

		return new BidRecord(player.name, price, house, refund);
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
		for (int i = houseIndex; i < players.length; i++) {
			pool.add(allHouse[i]);
		}

		houseIndex += players.length;
		Collections.sort(pool);
	}
}
