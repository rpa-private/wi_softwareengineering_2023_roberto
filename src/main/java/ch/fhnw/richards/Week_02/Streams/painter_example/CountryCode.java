package ch.fhnw.richards.Week_02.Streams.painter_example;

public enum CountryCode {
	AT("Austria"),
	CH("Switzerland"),
	DE("Germany"),
	ES("Spain"),
	FR("France"),
	IT("Italy"),
	NL("Netherlands"),
	NO("Norway"),
	US("United States");
	
	private String name;
	
	private CountryCode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
