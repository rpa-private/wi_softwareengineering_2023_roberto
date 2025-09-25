package ch.fhnw.richards.Week_02.Streams.painter_example;

public class Painter {
	private String name;
	private Integer birthYear;
	private Integer deathYear; // null if still alive
	private Gender gender;
	private CountryCode nationality;
	
	public static enum Gender { MALE, FEMALE };
	
	public Painter(String name, Integer birthYear, Gender gender, CountryCode nationality) {
		this.name = name;
		this.birthYear = birthYear;
		this.gender = gender;
		this.nationality = nationality;
	}
	
	public Painter(String name, Integer birthYear, Integer deathYear, Gender gender, CountryCode nationality) {
		this(name, birthYear, gender, nationality);
		this.deathYear = deathYear;
	}
	
	@Override
	public String toString() {
		if (deathYear != null)
			return name + " (" + birthYear + "-" + deathYear + ")";
		else
			return name + " (" + birthYear + "- )";		
	}

	// Getters
	public String getName() {
		return name;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public Integer getDeathYear() {
		return deathYear;
	}

	public Gender getGender() {
		return gender;
	}

	public CountryCode getNationality() {
		return nationality;
	}	
}
