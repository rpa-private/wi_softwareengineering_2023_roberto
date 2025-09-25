package ch.fhnw.richards.Week_01;

/**
 * Political entities must have a unique ID. For some entities, this is easily derived. For example, countries have
 * unique country codes (like CH for Switzerland). Entities within a country usually have an identifier given by their
 * country. For example, the abbreviations for cantons in Switzerland (BS, VS, BE, etc.), the county and town numbers
 * given in the "Gemeindeverzeichnis" https://www.bfs.admin.ch/bfs/de/home/grundlagen/agvch.assetdetail.23886073.html
 *
 * This class has been kept very simple for demonstration purposes.
 */
public record PoliticalUnit(String ID, String name) { }
