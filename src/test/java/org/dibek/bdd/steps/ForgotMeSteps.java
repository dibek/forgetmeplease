package org.dibek.bdd.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.dibek.ForgetMaPlease;
import org.dibek.ForgetMeWrapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by giuseppe.dibella on 20/12/2016.
 */
public class ForgotMeSteps {

    ForgetMaPlease<String> forgetMapPlease = new ForgetMaPlease<>(ForgetMeWrapper.createDefault("NotFound"),10,10);

    List<ForgetMeWrapper<String>> listWrappers = new ArrayList<>();

    private String myResult;

    @Given("I put in the map the following data$")
    public void putTermWithValue(List<String> listItems) {
        long count = 0;
        for (String value : listItems) {
            System.out.println("Added " + value+ "with Id " + count);
            forgetMapPlease.add(count++,  value);
        }
    }

    @When("I find a term with id: (\\d+)")
    public void findTermWithId(long id) {
        myResult =  forgetMapPlease.find(id);
    }

    @Then("^The expected term \"([^\"]*)\" is found$")
    public void  compareTerms(String actual){
        System.out.println(myResult);
        assertThat(actual, equalTo(myResult));
    }


}
