package com.chatbot.stockbot;

public class StockBotApplication {

	public static void main(String[] args) {
		String stockDataFilePath = "C:\\\\bi2i\\\\stock-bot\\\\stock_data.json";
		StockBot bot = new StockBot(stockDataFilePath);

	}

}
