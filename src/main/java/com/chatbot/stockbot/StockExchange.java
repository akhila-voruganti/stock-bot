package com.chatbot.stockbot;

import java.util.List;

public class StockExchange {
	private String code;
	private String stockExchange;
	private List<Stock> topStocks;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public List<Stock> getTopStocks() {
		return topStocks;
	}

	public void setTopStocks(List<Stock> topStocks) {
		this.topStocks = topStocks;
	}

}
