package backupTests;

import Solution.Given;
import Solution.Then;
import Solution.When;
import org.junit.Assert;

public class BearStoryTest {
	private Bear bear;
	
	@Given("a bear of age &age")
	public void aBear(Integer age) {
		bear = new Bear(age);
	}
	
	@When("he hunts for the duration of &hours")
	public void heHunts(Integer hours) {
		bear.hunt(hours);
	}

	@When("he sleeps for the duration of &hours")
	public void heSleeps(Integer hours) {
		bear.sleep(hours);
	}
	
	@Then("he feels &feeling")
	public void heFeels(String feeling) {
		Assert.assertEquals(feeling, bear.getFeeling());
	}
}
