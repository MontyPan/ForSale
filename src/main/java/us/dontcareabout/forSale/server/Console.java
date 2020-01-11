package us.dontcareabout.forSale.server;

import java.util.ArrayList;
import java.util.HashMap;

import us.dontcareabout.forSale.client.model.BidRecord;
import us.dontcareabout.forSale.client.model.GameMaster;
import us.dontcareabout.forSale.client.model.GameResult;
import us.dontcareabout.forSale.client.model.Registration;
import us.dontcareabout.forSale.client.model.SellRecord;
import us.dontcareabout.forSale.client.model.Util;
import us.dontcareabout.forSale.client.model.ai.Cheater;
import us.dontcareabout.forSale.client.model.ai.Dummy;
import us.dontcareabout.forSale.client.model.ai.Newbie;

public class Console {
	public static void main(String[] args) {
		GameMaster gm = new Registration()
			.signUp(new Newbie())
			.signUp(new Cheater())
			.signUp(new Newbie())
			.signUp(new Dummy())
			.signUp(new Newbie())
			.prepare();
		gm.start();

		printBR(gm.getBidRecords());
		printSR(gm.getSellRecords());
		printResult(gm.getGameResult());
	}

	private static void printResult(GameResult[] gameResult) {
		for (GameResult gr : gameResult) {
			System.out.println(Util.toString(gr));
		}
	}

	private static void printSR(HashMap<Integer, SellRecord[]> sellRecords) {
		for (int i = 1; i <= sellRecords.size(); i++) {
			SellRecord[] srList = sellRecords.get(i);
			for (SellRecord sr : srList) {
				System.out.println(Util.toString(sr));
			}
			System.out.println("==========================");
		}
	}

	public static void printBR(HashMap<Integer, ArrayList<BidRecord>> bidRecords) {
		for (int i = 1; i <= bidRecords.size(); i++) {
			ArrayList<BidRecord> brList = bidRecords.get(i);
			for (BidRecord br : brList) {
				System.out.println(Util.toString(br));
			}
			System.out.println("==========================");
		}
	}
}