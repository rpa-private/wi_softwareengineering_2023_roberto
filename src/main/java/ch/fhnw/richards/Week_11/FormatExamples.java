package ch.fhnw.richards.Week_11;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;

public class FormatExamples {
    private static Locale english = Locale.ENGLISH;
    private static Locale englishUK = Locale.forLanguageTag("en-UK");
    private static Locale german = Locale.GERMAN;
    private static Locale swissGerman = Locale.forLanguageTag("de-CH");
    public static void main(String[] args) {
        System.out.println("The default locale for this machine is " + Locale.getDefault().getDisplayName());
        System.out.println();
        System.out.println("The generic English locale " + english.getDisplayName());
        System.out.println("The English (UK) locale " + englishUK.getDisplayName());
        System.out.println("The generic German locale " + german.getDisplayName());
        System.out.println("The German (Swiss) locale " + swissGerman.getDisplayName());
        System.out.println();

        numberFormats();

        dateFormats();

        currencyFormats();

    }

    private static void numberFormats() {
        System.out.println("----- Number Format Examples -----");
        NumberFormat formatter = NumberFormat.getInstance(english);
        System.out.println("Generic English: " + formatter.format(1234567.89));
        formatter = NumberFormat.getInstance(englishUK);
        System.out.println("English UK: " + formatter.format(1234567.89));
        formatter = NumberFormat.getInstance(german);
        System.out.println("Generic German: " + formatter.format(1234567.89));
        formatter = NumberFormat.getInstance(swissGerman);
        System.out.println("Swiss German: " + formatter.format(1234567.89));
        System.out.println();
    }

    private static void dateFormats() {
        System.out.println("----- Date Format Examples -----");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(english);
        System.out.println("Generic English: " + today.format(formatter));
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(englishUK);
        System.out.println("English UK: " + today.format(formatter));
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(german);
        System.out.println("Generic German: " + today.format(formatter));
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(swissGerman);
        System.out.println("Swiss German: " + today.format(formatter));
        System.out.println();
    }

    private static void currencyFormats() {
        System.out.println("----- Currency Format Examples -----");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(english);
        formatter.setCurrency(Currency.getInstance("USD"));
        System.out.println("Generic English: " + formatter.format(1234567.89));
        formatter = NumberFormat.getCurrencyInstance(englishUK);
        formatter.setCurrency(Currency.getInstance("GBP"));
        System.out.println("English UK: " + formatter.format(1234567.89));
        formatter = NumberFormat.getCurrencyInstance(german);
        formatter.setCurrency(Currency.getInstance("EUR"));
        System.out.println("Generic German: " + formatter.format(1234567.89));
        formatter = NumberFormat.getCurrencyInstance(swissGerman);
        formatter.setCurrency(Currency.getInstance("CHF"));
        System.out.println("Swiss German: " + formatter.format(1234567.89));
        System.out.println();
    }
}
