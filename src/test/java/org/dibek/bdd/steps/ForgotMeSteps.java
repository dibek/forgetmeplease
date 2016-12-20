package org.dibek.bdd.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.dibek.ForgetMaPlease;
import org.dibek.ForgetMeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by giuseppe.dibella on 20/12/2016.
 */
public class ForgotMeSteps {

    private ForgetMaPlease<String> forgetMapPlease = new ForgetMaPlease<>(ForgetMeWrapper.createDefault("NotFound"),10,10);

    List<ForgetMeWrapper<String>> listWrappers = new ArrayList<>();

    private String myResult;

    @Given("I put in the map the following data$")
    public void putTermWithValue(Map<String,Integer> mapItems) {
        long count = 0;
        for (Map.Entry<String, Integer> entry : mapItems.entrySet()) {
            System.out.println("Added " + entry.getKey()+ "with Id " + count);
            forgetMapPlease.add(count++,entry.getKey(), entry.getValue());
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
