package backupTests;

import Solution.Given;
import Solution.Then;
import Solution.When;
import org.junit.Assert;

public class DragonStoryTest {
    protected Dragon dragon;

    @Given("a dragon of age &age")
    public void aDragon(Integer age) {
        dragon = new Dragon(age);
    }

    @When("he hunts for the duration of &hours")
    public void heHunts(Integer hours) {
        dragon.hunt(hours);
    }

    @When("he sleeps for the duration of &hours")
    public void heSleeps(Integer hours) {
        dragon.sleep(hours);
    }

    @Then("he feels &feeling")
    public void heFeels(String feeling) {
        Assert.assertEquals(feeling, dragon.getFeeling());
    }

    public class InnerClass extends DragonStoryTest {
        @Given("a golden dragon of age &age")
        public void aDragon2(Integer age) {
            dragon = new Dragon(age);
            // he starts with more power
            dragon.sleep(40);
        }
        @When("he rests &hours")
        private void heRest(Integer hours) {
            dragon.sleep(hours);
        }

    }
}