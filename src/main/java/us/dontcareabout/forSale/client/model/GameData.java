package us.dontcareabout.forSale.client.model;

import java.util.List;

public class GameData {
	private static final int[] INIT_MONEY = {18, 18, 14, 14};

	/** pass 時是否返回向下取整的金額 */
	public final boolean floorMode;

	private final Player[] players;
	private final int[] pool;

	private int nowPrice;
	private int nowPlayer;

	public GameData(List<String> nameList) {
		this(nameList, true);
	}

	public GameData(List<String> nameList, boolean floorMode) {
		this.floorMode = floorMode;
		int playerAmount = nameList.size();
		players = new Player[playerAmount];
		pool = new int[playerAmount];

		//TODO 亂數位置
		for (int i = 0; i < nameList.size(); i++) {
			players[i] = new Player(nameList.get(i), INIT_MONEY[nameList.size() - 3]);
		}
	}
}
