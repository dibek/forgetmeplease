package org.dibek;

import org.dibek.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;


/**
 * Created by giuseppe.dibella on 15/12/2016.
 */
public class ForgetMePleaseTest {

    ForgetMePlease<String> forgetMePlease = new ForgetMePlease<>(10);

    @Before
    public void setUp() throws Exception {
        fillForgetMap(10);
    }


    @Test
    public void addItemToForgettingMapTillLimitThenRemoveOlder() throws Throwable {
        assertThat(forgetMePlease.add(11,"aValue"), equalTo(10) );
        assertThat(forgetMePlease.add(12,"anotherValue"), equalTo(10) );
    }

    @Test
    public void addItemAndRetrieveIt() throws  Throwable{
        forgetMePlease.add(11,"aValue");
        String myValue = forgetMePlease.find(11);
        assertThat(myValue,equalTo("aValue"));
    }

    @Test(expected=NotFoundException.class)
    public void addItemOverLimitAndAssertFirstElementRemoved() throws NotFoundException {
        forgetMePlease.add(11,"aValue");
        forgetMePlease.find(0);
    }

    private void fillForgetMap(int limit) {
        for (int num = 0; num < limit;  num++) {
            forgetMePlease.add(num,"myValue" + num);
        }
    }

}