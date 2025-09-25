package ch.fhnw.richards.Week_13.sampleExam.problem_3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsQuestions {
    public static void main(String[] args) {
        List<Invoice> invoices = getData();
        System.out.println(example(invoices));

        System.out.println(part_A(invoices, 2023));

        for (Integer i : part_B(invoices)) System.out.print(i + " ");
        System.out.println();
    }

    private static Long example(List<Invoice> invoices) {
        return invoices.stream().count();
    }

    private static Integer part_A(List<Invoice> invoices, int year) {
        return invoices.stream()
                .filter(t -> t.getDate().getYear() == year)
                .sorted(Comparator.comparing(Invoice::getTotal))
                .findFirst()
                .get()
                .getTotal();
    }

    private static List<Integer> part_B(List<Invoice> invoices) {
        return invoices.stream()
                .map( invoice -> invoice.getTotal())
                .sorted()
                .collect(Collectors.toList());
    }

    private static List<Invoice> getData() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = new Invoice(LocalDate.of(2023, 10, 10), 1);
        invoice1.addLineItem(new LineItem(invoice1.getID(), 1, 1, 100));
        invoice1.addLineItem(new LineItem(invoice1.getID(), 2, 2, 50));
        invoices.add(invoice1);
        Invoice invoice2 = new Invoice(LocalDate.of(2023, 12, 12), 1);
        invoice2.addLineItem(new LineItem(invoice2.getID(), 2, 1, 50));
        invoice2.addLineItem(new LineItem(invoice2.getID(), 3, 1, 250));
        invoices.add(invoice2);
        Invoice invoice3 = new Invoice(LocalDate.of(2024, 10, 10), 1);
        invoice3.addLineItem(new LineItem(invoice3.getID(), 1, 1, 100));
        invoice3.addLineItem(new LineItem(invoice3.getID(), 3, 3, 250));
        invoices.add(invoice3);
        return invoices;
    }
}
