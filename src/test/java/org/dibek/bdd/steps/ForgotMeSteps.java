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


    private String myResult;

    @Given("I put in the map the following data$")
    public void putTermWithValue(Map<String,Integer> mapItems) {
        long count = 0;
        for (Map.Entry<String, Integer> entry : mapItems.entrySet()) {
            forgetMapPlease.add(count++,entry.getKey(), entry.getValue());
            System.out.println("Added " + entry.getKey()+ " with Id " + count);
        }
    }
    @And("I add to the exiting map the following data$")
    public void putOthersTermsWithValue(Map<String,Integer> mapItems) {
        long count = forgetMapPlease.size();
        for (Map.Entry<String, Integer> entry : mapItems.entrySet()) {
            forgetMapPlease.add(count++,entry.getKey(), entry.getValue());
            System.out.println("Added " + entry.getKey()+ " with Id " + count);
        }
    }

    @When("I find a term with id: (\\d+)")
    public void findTermWithId(long id) {
        myResult =  forgetMapPlease.find(id);
    }

    @And("I search a key with id (\\d+) for (\\d+)  times to increase its rank")
    public void searchIdToIncreaseRank(long key, int nTImes) {
        for (int i = 0; i < nTImes ; i++) {
            forgetMapPlease.find(key);
        }
    }

    @Then("^The expected term \"([^\"]*)\" is found$")
    public void  compareTerms(String actual){
        System.out.println("Find item: " + myResult);
        assertThat(actual, equalTo(myResult));
    }


}
