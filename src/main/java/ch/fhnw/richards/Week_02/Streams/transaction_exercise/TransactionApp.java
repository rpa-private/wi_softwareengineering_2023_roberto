package ch.fhnw.richards.Week_02.Streams.transaction_exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionApp {
    public static void main(String[] args) {
        List<Trader> traderList = Arrays.asList(
        new Trader("Raoul", "Basel"),
        new Trader("Mario", "Brugg"),
        new Trader("Alan", "Basel"),
        new Trader("Brian", "Basel"));

        List<Transaction> transactionList = Arrays.asList(
        new Transaction(traderList.get(3), 2011, 300),
        new Transaction(traderList.get(0), 2012, 1000),
        new Transaction(traderList.get(0), 2011, 400),
        new Transaction(traderList.get(1), 2012, 710),
        new Transaction(traderList.get(1), 2012, 700),
        new Transaction(traderList.get(2), 2012, 950));

        TransactionList tl = new TransactionList();
        transactionList.forEach(tl::addTransaction);

        String namen = tl.traders("Basel").stream()
                .map(Trader::getName)
                .collect(Collectors.joining(", "));
        System.out.println(namen);

    }
}
