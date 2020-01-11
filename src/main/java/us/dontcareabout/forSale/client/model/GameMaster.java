package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import us.dontcareabout.forSale.client.model.ai.AI;

public class GameMaster {
	private GameData game;
	private HashMap<String, AI> aiMap = new HashMap<>();
	private HashMap<Integer, ArrayList<BidRecord>> bidRecords = new HashMap<>();
	private HashMap<Integer, SellRecord[]> sellRecords = new HashMap<>();

	GameMaster(ArrayList<AI> aiList) {
		this(aiList, Util.genHouseDeck(), Util.genMoneyDeck());
	}

	GameMaster(ArrayList<AI> aiList, int[] houseDeck, int[] moneyDeck) {
		ArrayList<String> players = new ArrayList<>();

		for (int i = 0; i < aiList.size(); i++) {
			String name = "[" + i + "] " + aiList.get(i).getClass().getSimpleName();
			aiMap.put(name, aiList.get(i));
			players.add(name);
		}

		Collections.sort(players);
		game = new GameData(players, houseDeck, moneyDeck);
	}

	public void start() {
		for (AI ai : aiMap.values()) {
			ai.gameInit(game.getPlayerAmount(), game.getTurnAmount(), game.getInitMoney());
		}

		//拍賣階段
		for (int i = 1; i <= game.getTurnAmount(); i++) {
			int[] pool = game.getPool();

			ArrayList<BidRecord> brList = new ArrayList<>();
			bidRecords.put(i, brList);

			for (AI ai : aiMap.values()) { ai.newBidTurn(i, pool.clone()); };

			while(true) {
				int price = nowPlayer().bid(game.getPool(), game.getNowPrice());
				BidRecord br = game.bid(price);
				brList.add(br);
				for (AI ai : aiMap.values()) { ai.bidCourse(br); }

				//剩下一個人還沒 pass，自動讓他 pass 然後換下一個回合
				if (game.getPoolSize() == 1) {
					br = game.bid(0);
					brList.add(br);
					for (AI ai : aiMap.values()) { ai.bidCourse(br); }
					break;
				}
			}
		}

		//販賣階段
		for (int i = 1; i <= game.getTurnAmount(); i++) {
			int[] pool = game.getPool();
			HashMap<String, Integer> houseMap = new HashMap<>();

			for (String aiName : aiMap.keySet()) {
				//避免有人亂搞 pool，所以用上 clone 大法讓每個人拿到同樣內容的不同 instance
				houseMap.put(aiName, aiMap.get(aiName).sell(pool.clone()));
			}

			SellRecord[] srArray = game.sell(houseMap);
			sellRecords.put(i, srArray);

			for (SellRecord sr : srArray) {
				aiMap.get(sr.player).sellResult(srArray.clone());
			}
		}
	}

	public HashMap<Integer, ArrayList<BidRecord>> getBidRecords() {
		return bidRecords;
	}

	public HashMap<Integer, SellRecord[]> getSellRecords() {
		return sellRecords;
	}

	private AI nowPlayer() {
		return aiMap.get(game.getNowPlayer());
	}
}