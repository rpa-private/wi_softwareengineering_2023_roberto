module test {
    requires org.junit.jupiter.api;
    requires ch.fhnw.richards;

    opens ch.fhnw.richards.test.Week_01 to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Lambdas.countrySort_v1 to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Lambdas.countrySort_v2 to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Lambdas.countrySort_v3 to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Lambdas.countrySort_v4 to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Streams.loginRecord_example to org.junit.platform.commons;
    opens ch.fhnw.richards.test.Week_02.Streams.transaction_exercise to org.junit.platform.commons;
}