package backupTests;

import Solution.Given;
import Solution.Then;
import Solution.When;

public class DragonStoryDerivedTest extends DragonStoryTest{

    @When("he hunts for the duration of &hours")
    public void heHunts2(Integer hours) {
        Integer tmp = hours/5;
        dragon.hunt(tmp);
    }

    public class InnerInnerClass extends DragonStoryTest {
        @Given("a white dragon of age &age")
        public void aDragon4(Integer age) {
            dragon = new Dragon(age);
        }

        @When("he sleeps for the duration of &hours")
        public void heSleeps2(Integer hours) {
            Integer tmp = hours*2;
            dragon.sleep(tmp);
        }

        public class InnerInnerInnerClass extends InnerClass {
            @Given("a super dragon of age &age")
            public void aDragon3(Integer age) {
                dragon = new Dragon(age);
            }
        }
    }
}

