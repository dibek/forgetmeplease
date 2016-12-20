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

    ForgetMaPlease forgetMapPlease = (ForgetMaPlease<String>)
            new ForgetMaPlease.ForgetMePleaseBuilder()
            .size(10)
            .limitRank(20)
            .defaultValue("NotFound")
            .build();

    List<ForgetMeWrapper<String>> listWrappers = new ArrayList<>();

    private String myResult;

    @Given("I put in the map the following data$")
    public void putTermWithValue(List<ForgetMeWrapper> listWrappers) {
        for (ForgetMeWrapper wrapper : listWrappers) {
            System.out.println("Added " + wrapper.getValue()+ "with Id" + wrapper.getId());
            forgetMapPlease.add(wrapper.getId(),  wrapper.getValue());
        }
    }

    @When("I find a term with id: (\\d+)")
    public void findTermWithId(long id) {
        myResult =  (String) forgetMapPlease.find(id);
    }

    @Then("^The expected term \"([^\"]*)\" is found$")
    public void  compareTerms(String actual){
        System.out.println(myResult);
        assertThat(actual, equalTo(myResult));
    }


}
