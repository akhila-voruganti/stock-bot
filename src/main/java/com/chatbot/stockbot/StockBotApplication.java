package com.chatbot.stockbot;

public class StockBotApplication {

	public static void main(String[] args) {
		String stockDataFilePath = "<cloned-repo-location>//stock_data.json";
		StockBot bot = new StockBot(stockDataFilePath);

	}

}
