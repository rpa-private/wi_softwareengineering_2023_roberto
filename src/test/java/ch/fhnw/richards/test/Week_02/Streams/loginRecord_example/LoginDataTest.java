package ch.fhnw.richards.test.Week_02.Streams.loginRecord_example;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ch.fhnw.richards.Week_02.Streams.loginRecord_example.*;


public class LoginDataTest {
	
	//--- Create the test data ---
	private static final List<User> userData = Arrays.asList(new User(1, "Fred"), new User(2, "Sue"), new User(3, "Ann"),
			new User(4, "Tom"), new User(5, "Ralph"), new User(6, "Betty"), new User(7, "Sarah"), new User(8, "James"));

	/**
	 * For practice, we initialize the data using Streams. For each user, we
	 * iterate 12 times. For each iteration, we add a new access time, one in
	 * each month (with the day, hour and minute set randomly).
	 * 
	 * Note: This is harder to understand than ordinary loops. This is
	 * probably a good example of when *not* to use streams.
	 */
	static {
		Random rand = new Random();
		userData.stream().forEach(u -> {
			IntStream.iterate(1, i -> i + 1).limit(12)
			    .forEach(i -> u.addNewLogin(
			    		LocalDateTime.of(2017, i, rand.nextInt(28) + 1, rand.nextInt(24), rand.nextInt(59))
			    )
			);
		});
	}
	
	
	//--- Test methods ---

	/**
	 * 8 users
	 */
	@Test
	public void totalUsers() {
		assertEquals(LoginDataAnalysis.totalUsers(userData), 8);
	}
	
	/**
	 * Sorted user names
	 */
	@Test
	public void sortedUserNames() {
		List<String> names = LoginDataAnalysis.sortedUserNames(userData);
		for (int i = 0; i < names.size()-1; i++) {
			assertTrue(names.get(i).compareTo(names.get(i+1)) < 0);
		}
	}
	
	/**
	 * 12 accesses per user
	 */
	@Test
	public void numberAccessesOneUser() {
		List<LocalDateTime> accesses = LoginDataAnalysis.accessTimesOneUser(userData);
		assertEquals(accesses.size(), 12);
	}	

	/**
	 * 8 * 12 = 96 total accesses
	 */
	@Test
	public void numberAccesses() {
		assertEquals(LoginDataAnalysis.totalAccesses(userData), 96);
	}
	
	/**
	 * First access in January 2017
	 */
	@Test
	public void firstAccessDate() {
		LocalDateTime firstAccess = LoginDataAnalysis.firstAccess(userData);
		assertEquals(firstAccess.getYear(), 2017);
		assertEquals(firstAccess.getMonthValue(), 1);
	}
}
