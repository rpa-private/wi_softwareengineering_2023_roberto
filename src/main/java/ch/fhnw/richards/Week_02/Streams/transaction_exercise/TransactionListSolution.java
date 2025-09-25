package ch.fhnw.richards.Week_02.Streams.transaction_exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dieter Holz
 */
public class TransactionListSolution {
	private final List<Transaction> allTransactions = new ArrayList<>();

	public void addTransaction(Transaction transaction) {
		allTransactions.add(transaction);
	}

	public int size() {
		return allTransactions.size();
	}

	/**
	 * @param year the year
	 * @return all transactions from given year sorted by value (small to high)
	 */
 	public List<Transaction> transactionsInYear(int year) {
		return allTransactions.stream()
				.filter(t -> t.getYear() == year)
				.sorted(Comparator.comparing(Transaction::getValue))
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @return all the unique cities where the traders work
	 */
	public List<String> cities() {
		return allTransactions.stream()
				.map(transaction -> transaction.getTrader().getCity())
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @param city the trader's city
	 * @return all traders from given city sorted by name.
	 */
	public List<Trader> traders(String city) {
		return allTransactions.stream()
				.map(Transaction::getTrader)
				.filter(trader -> trader.getCity().equals(city))
				.distinct()
				.sorted(Comparator.comparing(Trader::getName))
				.collect(Collectors.toList());
	}



	/**
	 *
	 * @param city the city
	 * @return true if there are any trader based in given city
	 */
	public boolean traderInCity(String city) {
		return allTransactions.stream()
				.anyMatch(transaction -> transaction.getTrader()
								.getCity()
								.equals(city)
				);
	}

	/**
	 *
	 * @param from the trader's current location
	 * @param to the trader's new location
	 */
	public void relocateTraders(String from, String to) {
		allTransactions.stream()
              .map(Transaction::getTrader)
              .filter(trader -> trader.getCity().equals(from))
              .forEach(trader -> trader.setCity(to));
	}

	/**
	 *
	 * @return the highest value in all the transactions
	 */
	public int highestValue() {
		return allTransactions.stream()
				.map(Transaction::getValue)
				.reduce(0, Integer::max);
	}

	/**
	 *
	 * @return a string of all traders’ names sorted alphabetically
	 */
	public String traderNames() {
        return allTransactions.stream()
                              .map(transaction -> transaction.getTrader().getName())
                              .distinct()
                              .sorted()
                              .collect(Collectors.joining());
        //.reduce("", (n1, n2) -> n1 + n2);
	}
}
