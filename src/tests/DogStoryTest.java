package tests;

import Solution.*;
import org.junit.Assert;

public class DogStoryTest {
    protected tests.Dog dog;
    public ForTestCloneable fTC;
    public ForTestNoClone_CopyCtor fTNC;
    public ForTestNoCloneNoCopyCtor fTNCNC;

    @Given("a Dog of age &age")
    public void aDog(Integer age) {
        fTC = new ForTestCloneable();
        fTNC = new ForTestNoClone_CopyCtor();
        fTNCNC = new ForTestNoCloneNoCopyCtor();
        dog = new Dog(age);
        System.out.println("Given a Dog of age " + age.toString());
    }

    @When("the dog is not taken out for a walk, the number of hours is &hours")
    public void dogNotTakenForAWalk(Integer hours) {
        dog.notTakenForAWalk(hours);
        System.out.println("When the dog is not taken out for a walk, the number of hours is "
                + hours.toString());
    }

    @Then("the house condition is &condition")
    public void theHouseCondition(String condition) {
        System.out.println(fTC.num + ", " + fTNC.num + ", " + fTNCNC.num);
        System.out.println("Then the house condition is "
                + condition.toString());

        Assert.assertEquals(condition, dog.houseCondition());
    }
}
