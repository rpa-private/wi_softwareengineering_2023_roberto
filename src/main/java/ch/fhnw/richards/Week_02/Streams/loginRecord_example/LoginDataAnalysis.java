package ch.fhnw.richards.Week_02.Streams.loginRecord_example;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class includes several methods to analyse the login data.
 * 
 * All methods should be implemented using streams!
 */
public class LoginDataAnalysis {
	
	/**
	 * Number of user records
	 */
	public static long totalUsers(List<User> userData) {
		return userData.stream().count();
	}
	
	/**
	 * Sort the user records sorted by loginName; return the names as a List
	 */
	public static List<String> sortedUserNames(List<User> userData) {
		return userData.stream()
				.sorted( Comparator.comparing(User::getLoginName) )
				.map(User::getLoginName)
				.collect(Collectors.toList());
	}
	
	/**
	 * Return the access times of a random user
	 */
	public static List<LocalDateTime> accessTimesOneUser(List<User> userData) {
		return userData.stream()
				.findAny().get().getAccessTimes();
	}
	
	/**
	 * Use "flatMap" to create a list of all access times of all users,
	 * then count the total number of accesses 
	 */
	public static long totalAccesses(List<User> userData) {
		return userData.stream()
				.flatMap(u -> u.getAccessTimes().stream())
				.count();
	}
	
	/**
	 * The date and time of the first access by anyone
	 */
	public static LocalDateTime firstAccess(List<User> userData) {
		return userData.stream()
				.flatMap(u -> u.getAccessTimes().stream())
				.min(Comparator.naturalOrder()).get();		
	}
}
