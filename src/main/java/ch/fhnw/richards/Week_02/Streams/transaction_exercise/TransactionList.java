package ch.fhnw.richards.Week_02.Streams.transaction_exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dieter Holz
 */
public class TransactionList {

	private final List<Transaction> allTransactions = new ArrayList<>();
	private final List<Trader> allTraders = new ArrayList<>();

	public void addTransaction(Transaction transaction) {
		allTransactions.add(transaction);
	}
	public void addTraders(Trader trader) {
		allTraders.add(trader);
	}

	public int size() {
		return allTransactions.size();
	}

	public List<Transaction> transactionsInYear(int year) {
        return null;
    }

	public List<String> cities() {
        return null;
    }

	/**
	 * @param city the trader's city
	 * @return all traders from given city sorted by name.
	 */
	public List<Trader> traders(String city) {
		return allTransactions.stream()
				.map(Transaction::getTrader)
				.filter(trader -> city.equals(trader.getCity()))
				.distinct()
				.sorted(Comparator.comparing(Trader::getName))
				.collect(Collectors.toList());
	}

	/**
	 * @param city the city
	 * @return true if there are any trader based in given city
	 */
	public boolean traderInCity(String city) {
        return false;
    }

	/**
	 * @param from the trader's current location
	 * @param to   the trader's new location
	 */
	public void relocateTraders(String from, String to) {

	}

	/**
	 * @return the highest value in all the transactions
	 */
	public int highestValue() {
		return 0;
	}

	/**
	 * @return a string of all traders’ names sorted alphabetically
	 */
	public String traderNames() {
        return null;
    }

}
