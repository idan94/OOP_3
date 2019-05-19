package backupTests;

import Solution.Given;
import Solution.Then;
import Solution.When;
import org.junit.Assert;

public class TigerStoryTest {
	private Tiger tiger;
	
	@Given("a tiger of age &age")
	public void aTiger(Integer age) {
		tiger = new Tiger(age);
	}
	
	@When("he hunts for the duration of &hours")
	public void heHunts(Integer hours) {
		tiger.hunt(hours);
	}

	@When("he sleeps for the duration of &hours")
	public void heSleeps(Integer hours) {
		tiger.sleep(hours);
	}
	
	@Then("he feels &feeling")
	public void heFeels(String feeling) {
		Assert.assertEquals(feeling, tiger.getFeeling());
	}
}
