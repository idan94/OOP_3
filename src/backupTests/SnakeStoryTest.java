package backupTests;

import Solution.Given;
import Solution.Then;
import Solution.When;
import org.junit.Assert;

public class SnakeStoryTest {
	private Snake snake;
	
	@Given("a snake of age &age")
	public void aBear(Integer age) {
		snake = new Snake(age);
	}
	
	@When("he hunts for the duration of &hours")
	public void heHunts(Integer hours) {
		snake.hunt(hours);
	}

	@When("he sleeps for the duration of &hours")
	public void heSleeps(Integer hours) {
		snake.sleep(hours);
	}
	
	@Then("he feels &feeling")
	public void heFeels(String feeling) {
		Assert.assertEquals(feeling, snake.getFeeling());
	}
}
