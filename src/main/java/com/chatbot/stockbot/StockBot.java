package com.chatbot.stockbot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class StockBot {
	private List<StockExchange> stockExchanges;
	private Scanner scanner;

	/**
	 * constructor for stock bot 
	 * reads the input from stock-data.json maps data to
	 * java objs runs the application
	 */
	public StockBot(String jsonDataFilePath) {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(jsonDataFilePath)) {
			Type listType = new TypeToken<List<StockExchange>>() {
			}.getType();
			stockExchanges = gson.fromJson(reader, listType);
			if (stockExchanges == null || stockExchanges.isEmpty() || containsNullElements(stockExchanges)) {
				// Handle the case where the JSON file is empty, contains incorrect values, or
				// has null elements
				throw new NullPointerException();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: JSON file not found in given location.Please check.");
			return;
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please enter a valid number.");
			scanner.nextLine(); // Consume invalid input
		} catch (JsonSyntaxException e) {
			System.err.println("Error: JSON syntax error. Please check the format of the JSON file.");
			return;
		} catch (NullPointerException e) {
			System.err.println("Error: JSON file is empty, contains incorrect values, or has null elements.");
			return;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		scanner = new Scanner(System.in);
		run();
	}

	/**
	 * checks for null elements in the stock-data json for exchange stocks and stock
	 * details return true if null returns false if values are not null
	 */
	private boolean containsNullElements(List<StockExchange> exchanges) {
		for (StockExchange exchange : exchanges) {
			if (exchange == null || exchange.getTopStocks() == null || exchange.getTopStocks().contains(null)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * displays list of stock exchanges provides option to exit the application
	 * validates user input
	 */
	public void displayHomeMenu() {
		System.out.println("Home Menu:");
		int i = 0;
		for (i = 0; i < stockExchanges.size(); i++) {
			System.out.println(i + 1 + "." + " " + stockExchanges.get(i).getStockExchange());
		}

		System.out.println(i + 1 + ". Exit");
	}

	/**
	 * displays the list of top n stocks of user selected stock exchange
	 * 
	 * @param exchange - user selected stock exchange
	 * @param n-       required size of stocks list to be displayed set it to 5 as
	 *                 per requirement
	 */

	public void displayStockMenu(StockExchange exchange, int n) {
		System.out.println("Stock Menu for " + exchange.getStockExchange() + ":");
		List<Stock> topStocks = exchange.getTopStocks();
		int i = 0;
		for (i = 0; i < Math.min(n, topStocks.size()); i++) {
			Stock stock = topStocks.get(i);
			System.out.println((i + 1) + ". " + stock.getStockName());
		}
		System.out.println((i + 1) + ". Go to Home Menu");

	}

	/**
	 * displays name and price of given stock
	 * 
	 * @param stock user selected stock
	 */

	public void displayStockPrice(Stock stock) {
		System.out.println("Current price of " + stock.getStockName() + ": " + stock.getPrice());
	}

	/**
	 * depicts the workflow of stock bot based on user input calls displayStockMenu
	 * with selected stock exchange and limit stocks to 5 as per requirement calls
	 * handleStockMenuInput with selected stock exchange and limit stocks to 5 as
	 * per requirement validates user input
	 */
	public void run() {
		while (true) {
			try {
				displayHomeMenu();
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice >= 1 && choice <= stockExchanges.size()) {
					displayStockMenu(stockExchanges.get(choice - 1), 5);
					handleStockMenuInput(stockExchanges.get(choice - 1), 5);
				} else if (choice == stockExchanges.size() + 1) {
					System.out.println("Exiting....Closed the conversation.");
					return;
				} else {
					System.out.println(
							"Invalid choice. Please enter a number from 1 to " + (stockExchanges.size() + 1) + ".");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println(
						"Invalid choice. Please enter a number from 1 to " + (stockExchanges.size() + 1) + ".");
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number.");
				scanner.nextLine(); // Consume invalid input
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * handles the user stock selection calls the displayStockPrice function with
	 * selected stock validates the user choice provides option for home menu
	 * 
	 * @param exchange user selected stock exchange
	 * @param n        top n stocks for the given exchange
	 */

	public void handleStockMenuInput(StockExchange exchange, int n) {
		while (true) {
			try {
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				int limit = Math.min(n, exchange.getTopStocks().size());
				if (choice == limit + 1) {
					return;
				} else if (choice >= 1 && choice <= n) {
					Stock selectedStock = exchange.getTopStocks().get(choice - 1);
					displayStockPrice(selectedStock);
				} else {
					System.out.println("Invalid choice. Please enter a number from 1 to " + (limit + 1) + ".");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number.");
				scanner.nextLine(); // Consume invalid input
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid choice. Please enter a number");
			}
		}
	}

}
