package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;
import java.util.List;

public class GameData {
	private static final int[] INIT_MONEY = {18, 18, 14, 14};

	/** pass 時是否返回向下取整的金額 */
	public final boolean floorMode;

	private final Player[] players;
	private final ArrayList<Integer> pool = new ArrayList<>();

	private int nowPrice;
	private int nowPlayer;

	public GameData(List<String> nameList) {
		this(nameList, true);
	}

	public GameData(List<String> nameList, boolean floorMode) {
		this.floorMode = floorMode;
		int playerAmount = nameList.size();
		players = new Player[playerAmount];

		//TODO 亂數位置
		for (int i = 0; i < nameList.size(); i++) {
			players[i] = new Player(nameList.get(i), INIT_MONEY[nameList.size() - 3]);
		}
	}

	public int getTurnAmount() {
		return 11 - players.length;
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
}
