package us.dontcareabout.forSale.client.model;

import java.util.ArrayList;

import com.google.common.base.Preconditions;

import us.dontcareabout.forSale.client.model.ai.AI;

/**
 * 報名處。實際意義為提供 fluent style 的 {@link GameMaster} 產生方式。
 * {@link #signUp(AI)} 的先後順序即為遊戲中的先後順序，
 * 第一個 {@link #signUp(AI)} 的 {@link AI} 即為第一回合的起始玩家
 */
public class Registration {
	private ArrayList<AI> aiList = new ArrayList<>();

	public GameMaster prepare() {
		Preconditions.checkState(aiList.size() >= 3, "玩家人數必須三人以上");
		Preconditions.checkState(aiList.size() <= 6, "玩家人數必須六人以下");
		return new GameMaster(aiList);
	}

	public Registration signUp(AI ai) {
		aiList.add(ai);
		return this;
	}
}
