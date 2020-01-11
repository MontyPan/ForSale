遊戲規則
========

* [文字版](https://phantasia0021.pixnet.net/blog/post/330772462-%E5%9C%B0%E7%94%A2%E9%81%94%E4%BA%BA%EF%BC%88for-sale%EF%BC%89%E8%A6%8F%E5%89%87%2B%E5%BF%83%E5%BE%97)
* [視頻版](https://www.youtube.com/watch?v=bsdk-m-GftY)

[BGA](https://boardgamearena.com/gamepanel?game=forsale) 上頭也可以免費玩，不過要等付費玩家開桌......


開發環境
========

* JDK 7.0+、Maven 3.0+
* 請先 clone [GF](https://github.com/DontCareAbout/GF)，並對這個 Maven repo 作 `mvn install`


如何製作 AI
===========

1. 宣告一個 implement `AI` 的 class，假設叫做 `Foo`
1. 實作 `bid()`、`sell()` 等 method
	* 規格面請參閱 `us.dontcareabout.forSale.client.model.ai.AI` 的 JavaDoc
	* 實作面可參考 `us.dontcareabout.forSale.client.model.ai.Newbie`
1. 修改 `Console`，用 `Foo` 取代其中一個 `signUp()` 的參數
1. 執行 `Console` 就可以看到遊戲過程與結果
