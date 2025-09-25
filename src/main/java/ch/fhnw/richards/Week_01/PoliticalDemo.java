package ch.fhnw.richards.Week_01;

import java.util.List;

public class PoliticalDemo {
    public static void main(String[] args) {
        Tree<PoliticalUnit> switzerland = createSwitzerland();

        System.out.println("The country is: " + switzerland.getRoot().name());
        System.out.println();

        System.out.println("The cantons in Switzerland are: ");
        List<PoliticalUnit> cantons = switzerland.getChildren(new PoliticalUnit("CH", "Switzerland"));
        for (PoliticalUnit pu : cantons) System.out.println(pu.name() + " ");
        System.out.println();

        System.out.println("The counties in Solothurn are: ");
        List<PoliticalUnit> counties = switzerland.getChildren(new PoliticalUnit("SO", "Solothurn"));
        for (PoliticalUnit pu : counties) System.out.println(pu.name() + " ");
    }

    private static Tree<PoliticalUnit> createSwitzerland() {
        Tree<PoliticalUnit> switzerland = new Tree<>();
        PoliticalUnit country = new PoliticalUnit("CH", "Switzerland");
        switzerland.setRoot(country);

        switzerland.addChild(country, new PoliticalUnit("AG", "Aargau"));
        switzerland.addChild(country, new PoliticalUnit("AI", "Appenzell Innerrhoden"));
        switzerland.addChild(country, new PoliticalUnit("AR", "Appenzell Ausserrhoden"));
        switzerland.addChild(country, new PoliticalUnit("BE", "Bern"));
        switzerland.addChild(country, new PoliticalUnit("BL", "Basel-Landschaft"));
        switzerland.addChild(country, new PoliticalUnit("BS", "Basel-Stadt"));
        switzerland.addChild(country, new PoliticalUnit("FR", "Freiburg"));
        switzerland.addChild(country, new PoliticalUnit("GE", "Genf"));
        switzerland.addChild(country, new PoliticalUnit("GL", "Glarus"));
        switzerland.addChild(country, new PoliticalUnit("GR", "Graubünden"));
        switzerland.addChild(country, new PoliticalUnit("JU", "Jura"));
        switzerland.addChild(country, new PoliticalUnit("LU", "Luzern"));
        switzerland.addChild(country, new PoliticalUnit("NE", "Neuenburg"));
        switzerland.addChild(country, new PoliticalUnit("NW", "Nidwalden"));
        switzerland.addChild(country, new PoliticalUnit("OW", "Obwalden"));
        switzerland.addChild(country, new PoliticalUnit("SG", "St. Gallen"));
        switzerland.addChild(country, new PoliticalUnit("SH", "Schaffhausen"));
        switzerland.addChild(country, new PoliticalUnit("SO", "Solothurn"));
        switzerland.addChild(country, new PoliticalUnit("SZ", "Schwyz"));
        switzerland.addChild(country, new PoliticalUnit("TG", "Thurgau"));
        switzerland.addChild(country, new PoliticalUnit("TI", "Tessin"));
        switzerland.addChild(country, new PoliticalUnit("UR", "Uri"));
        switzerland.addChild(country, new PoliticalUnit("VD", "Waadt"));
        switzerland.addChild(country, new PoliticalUnit("VS", "Wallis"));
        switzerland.addChild(country, new PoliticalUnit("ZG", "Zug"));
        switzerland.addChild(country, new PoliticalUnit("ZH", "Zürich"));

        PoliticalUnit solothurn = new PoliticalUnit("SO", "Solothurn");
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Thal"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Bucheggberg"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Dorneck"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Gösgen"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Wasseramt"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Lebern"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Olten"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Solothurn"));
        switzerland.addChild(solothurn, new PoliticalUnit("1102", "Thierstein"));

        return switzerland;
    }
}
