package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameData {
	private static final int[] INIT_MONEY = {18, 18, 14, 14};

	private final Player[] players;
	private final ArrayList<Integer> pool = new ArrayList<>();
	private final int[] houseDeck;
	private final int[] moneyDeck;

	/** 紀錄 allHouse / allMoney 出到第幾張 */
	private int cardIndex;

	private int nowTurn;
	private int nowPlayer;
	private int nowPrice;

	public GameData(List<String> nameList, int[] houseDeck, int[] moneyDeck) {
		int playerAmount = nameList.size();
		players = new Player[playerAmount];
		this.houseDeck = houseDeck;
		this.moneyDeck = moneyDeck;

		for (int i = 0; i < nameList.size(); i++) {
			players[i] = new Player(nameList.get(i), getInitMoney());
		}

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
		int refund = getPoolSize() == 0 ? 0 : player.refund();

		//這裡也是觸發下一個回合的時機點，為了簡潔起見在這裡處理
		if (getPoolSize() == 0) {
			if (nowTurn < getTurnAmount()) {
				newBidTurn();
			} else {
				prepareSellMode();
			}
		}

		return new BidRecord(player.name, price, house, refund);
	}

	public SellRecord[] sell(HashMap<String, Integer> houseMap) {
		//相對於 houseMap 的反向 hash map
		//houseMap 當中的 value 值是可以重複的（例如 AI 都亂出 30）
		//所以需要下面的檢查校正然後才能丟進 inverse
		HashMap<Integer, String> inverse = new HashMap<>();

		for (String playerName : houseMap.keySet()) {
			ArrayList<Integer> ownHouse = players[findPlayer(playerName)].getOwnHouse();
			int house = houseMap.get(playerName);
			//如果要賣的房子不是自己擁有的房子，就自動校正成最小的（AI.sell()）
			house = ownHouse.contains(house) ? house : ownHouse.get(0);
			inverse.put(house, playerName);
		}

		ArrayList<Integer> houses = new ArrayList<>(inverse.keySet());
		Collections.sort(houses);

		SellRecord[] result = new SellRecord[players.length];
		int index = 0;

		for (Integer house : houses) {
			String playerName = inverse.get(house);
			players[findPlayer(playerName)].sell(house, pool.get(index));
			result[index] = new SellRecord(playerName, house, pool.get(index));
			index++;
		}

		if (nowTurn < getTurnAmount()) {
			pool.clear();
			newSellTurn();
		}

		return result;
	}

	/**
	 * @return 各玩家的最終金錢狀況，並透過 array index 紀錄排名，index 越小名次越高。
	 */
	public GameResult[] getResult() {
		GameResult[] result = new GameResult[players.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = new GameResult(players[i].name, players[i].getProfit(), players[i].getMoney());
		}

		Arrays.sort(result);
		return result;
	}

	private int findPlayer(String playerName) {
		for (int i = 0; i < players.length; i++) {
			if (playerName.equals(players[i].name)) {
				return i;
			}
		}

		return 0;	//不應該發生
	}

	private int nextBidPlayer() {
		for (int i = 1; i < players.length; i++) {
			if (!players[(nowPlayer + i) % players.length].isPass()) {
				return (nowPlayer + i) % players.length;
			}
		}

		return nowPlayer;
	}

	private void prepareSellMode() {
		nowTurn = 0;
		cardIndex = 0;
		newSellTurn();

		for (int i = 0; i < players.length; i++) {
			Collections.sort(players[i].getOwnHouse());
		}
	}

	private void newBidTurn() {
		nowTurn++;
		nowPrice = 0;

		for (int i = 0; i < players.length; i++) {
			pool.add(houseDeck[cardIndex]);
			cardIndex++;
		}

		Collections.sort(pool);

		for (Player player : players) {
			player.clearBid();
		}
	}

	private void newSellTurn() {
		nowTurn++;

		for (int i = 0; i < players.length; i++) {
			pool.add(moneyDeck[cardIndex]);
			cardIndex++;
		}

		Collections.sort(pool);
	}
}
