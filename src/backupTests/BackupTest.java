package backupTests;

/**
 * Lion - has clone
 * Tiger - has copy constructor
 * Bear - has nothing
 * Snake - has both clone and copy constructor
 **/


import Provided.StoryTestException;
import Provided.StoryTester;
import Solution.StoryTesterImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class BackupTest {

	private StoryTester tester;
	private String lionStory;
	private String lionStory2;
	private String tigerStory;
	private String bearStory;
	private String snakeStory;
	private Class<?> lionTestClass;
	private Class<?> tigerTestClass;
	private Class<?> bearTestClass;
	private Class<?> snakeTestClass;

	@Before
	public void setUp() throws Exception {
		lionStory = "Given a lion of age 6\n"
				+ "When he hunts for the duration of 20\n"
				+ "Then he feels Fine\n"
				+ "When he hunts for the duration of 1\n"
				+ "Then he feels Fine";
		lionStory2 = "Given a lion of age 6\n"
				+ "When he hunts for the duration of 20\n"
				+ "When he sleeps for the duration of 15\n"
				+ "Then he feels Tired\n"
				+ "When he hunts for the duration of 10\n"
				+ "When he sleeps for the duration of 5\n"
				+ "Then he feels Fine";
		tigerStory = "Given a tiger of age 6\n"
				+ "When he hunts for the duration of 20\n"
				+ "Then he feels Fine\n"
				+ "When he hunts for the duration of 1\n"
				+ "Then he feels Fine";
		bearStory = "Given a bear of age 6\n"
				+ "When he hunts for the duration of 20\n"
				+ "Then he feels Fine\n"
				+ "When he hunts for the duration of 1\n"
				+ "Then he feels Fine";
		snakeStory = "Given a snake of age 6\n"
				+ "When he hunts for the duration of 20\n"
				+ "Then he feels Fine\n"
				+ "When he hunts for the duration of 1\n"
				+ "Then he feels Fine";

		lionTestClass = LionStoryTest.class;
		tigerTestClass = TigerStoryTest.class;
		bearTestClass = BearStoryTest.class;
		snakeTestClass = SnakeStoryTest.class;
		tester = new StoryTesterImpl();
	}



	/**
	 * First then should fail because lion is tired
	 * Backup should revert to clone
	 * Second then should pass because lion is fine
	 **/
	@Test
	public void lionTest() throws Exception {
		try {
			tester.testOnInheritanceTree(lionStory, lionTestClass);
			Assert.assertTrue(false);
		} catch (StoryTestException e) {
			Assert.assertTrue(true);
			Assert.assertEquals("Then he feels Fine", e.getSentance());
			Assert.assertEquals(Arrays.asList("Fine"), e.getStoryExpected());
			Assert.assertEquals(Arrays.asList("Tired"), e.getTestResult());
			Assert.assertEquals(1, e.getNumFail());
		}
	}

	/**
	 * First then should fail because lion is fine (after sleeping)
	 * Backup should revert to clone with 0 exhaustion
	 * Second then should pass because lion is fine
	 * If this test fails, you might have saved a backup when you shouldn't have
	 **/
	@Test
	public void lionTest2() throws Exception {
		try {
			tester.testOnInheritanceTree(lionStory2, lionTestClass);
			Assert.assertTrue(false);
		} catch (StoryTestException e) {
			Assert.assertTrue(true);
			Assert.assertEquals("Then he feels Tired", e.getSentance());
			Assert.assertEquals(Arrays.asList("Tired"), e.getStoryExpected());
			Assert.assertEquals(Arrays.asList("Fine"), e.getTestResult());
			Assert.assertEquals(1, e.getNumFail());
		}
	}

	/**
	 * First then should fail because tiger is tired
	 * Backup should revert to copy constructor
	 * Second then should pass because tiger is fine
	 **/
	@Test
	public void tigerTest() throws Exception {
		try {
			tester.testOnInheritanceTree(tigerStory, tigerTestClass);
			Assert.assertTrue(false);
		} catch (StoryTestException e) {
			Assert.assertTrue(true);
			Assert.assertEquals("Then he feels Fine", e.getSentance());
			Assert.assertEquals(Arrays.asList("Fine"), e.getStoryExpected());
			Assert.assertEquals(Arrays.asList("Tired"), e.getTestResult());
			Assert.assertEquals(1, e.getNumFail());
		}
	}

	/**
	 * First then should fail because bear is tired
	 * Backup shouldn't do anything (because it's pointing to the same address as original)
	 * Second then should fail because bear is still tired
	 **/
	@Test
	public void bearTest() throws Exception {
		try {
			tester.testOnInheritanceTree(bearStory, bearTestClass);
			Assert.assertTrue(false);
		} catch (StoryTestException e) {
			Assert.assertTrue(true);
			Assert.assertEquals("Then he feels Fine", e.getSentance());
			Assert.assertEquals(Arrays.asList("Fine"), e.getStoryExpected());
			Assert.assertEquals(Arrays.asList("Tired"), e.getTestResult());
			Assert.assertEquals(2, e.getNumFail());
		}
	}

	/**
	 * First then should fail because snake is tired
	 * Backup should revert to clone
	 * Copy constructor doesn't actually copy, it sets exhaustion to a high number
	 * This means if the second then failed, you used copy constructor when you could have used clone
	 * Second then should pass because snake is fine
	 **/
	@Test
	public void snakeTest() throws Exception {
		try {
			tester.testOnInheritanceTree(snakeStory, snakeTestClass);
			Assert.assertTrue(false);
		} catch (StoryTestException e) {
			Assert.assertTrue(true);
			Assert.assertEquals("Then he feels Fine", e.getSentance());
			Assert.assertEquals(Arrays.asList("Fine"), e.getStoryExpected());
			Assert.assertEquals(Arrays.asList("Tired"), e.getTestResult());
			Assert.assertEquals(1, e.getNumFail());
		}
	}

}
