package ch.fhnw.richards.Week_02.Streams.loginRecord_example;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents a fictional user of some IT system. We maintain a list
 * of the times that the user accessed the system.
 * 
 * Note: In this example, we use LocalDateTime for convenience of creating the
 * data. However, if this were a real application, it would be better to use
 * java.time.Instant
 */
public class User {
	// All attributes immutable
	private final int ID;
	private final String loginName;
	private final ArrayList<LocalDateTime> accessTimes;

	public User(int ID, String loginName) {
		this.ID = ID;
		this.loginName = loginName;
		accessTimes = new ArrayList<>();
	}

	public void addNewLogin(LocalDateTime loginTime) {
		accessTimes.add(loginTime);
	}

	// Getters
	
	public int getID() {
		return ID;
	}

	public String getLoginName() {
		return loginName;
	}

	public ArrayList<LocalDateTime> getAccessTimes() {
		return accessTimes;
	}
}
