package us.dontcareabout.forSale.client.model.ai;

import us.dontcareabout.forSale.client.model.BidRecord;
import us.dontcareabout.forSale.client.model.SellRecord;

public interface AI {
	/**
	 * @param playerAmount 玩家總數
	 * @param turnAmount 拍賣 / 販售模式有幾個回合
	 * @param initMoney 初始金額
	 */
	void gameInit(int playerAmount, int turnAmount, int initMoney);

	/**
	 * @param pool 回合初始時的房屋清單
	 */
	//有可能在該輪第一次呼叫到 bid() 時，已經有人 pass 拿走房子
	//所以在每一輪開始前一律都把完整 pool 傳下去。
	void newBidTurn(int turnNumber, int[] pool);

	/**
	 * @param pool	目前剩餘的房屋清單
	 * @param nowPrice 目前出價金額
	 * @return 此次喊價金額。
	 *  	若值違反規則（小於等於目前出價金額、超出手上剩餘金額）
	 *  	則一律視為 pass。
	 */
	int bid(int[] pool, int nowPrice);

	/**
	 * 每個玩家執行完拍賣行動後會呼叫此 method 以接收該玩家行動內容。
	 */
	void bidCourse(BidRecord record);

	/**
	 * @param pool 金錢清單
	 * @return 此次販售的房屋。
	 * 		若值並非（仍）持有房屋，則自動修正為目前持有房屋的最低值
	 */
	int sell(int[] pool);

	/**
	 * 販售結果
	 */
	void sellResult(SellRecord[] records);
}
