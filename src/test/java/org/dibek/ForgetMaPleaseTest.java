package org.dibek;

import org.dibek.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Created by giuseppe.dibella on 15/12/2016.
 */
public class ForgetMaPleaseTest {

    private static final int LIMIT_MAP = 10;
    ForgetMaPlease<String> forgetMaPlease = new ForgetMaPlease.ForgetMePleaseBuilder<String>()
            .size(LIMIT_MAP)
            .limitRank(10)
            .defaultValue("Not Found")
            .build();

    @Before
    public void setUp() throws Exception {
        fillForgetMap(LIMIT_MAP);
    }


    @Test
    public void addItemDoentChangeSizeMap() throws Throwable {
        assertThat(forgetMaPlease.add(LIMIT_MAP+1L,"aValue",5), equalTo(LIMIT_MAP) );
        assertThat("The size of the map should be equals to", forgetMaPlease.add(LIMIT_MAP+2L,"anotherValue",5), equalTo(LIMIT_MAP) );
    }

    @Test
    public void addItemWithLowRankToBeSoonRemoved() throws Throwable {
        // given
        forgetMaPlease.add(LIMIT_MAP+1L,"aLowRankOne",0);
        //when
        forgetMaPlease.add(LIMIT_MAP+2L,"aHigherRankOne",10);
        forgetMaPlease.add(LIMIT_MAP+3L,"aMediumRankOne",5);
        // then
        assertThat("The key is not present anymore", forgetMaPlease.find(LIMIT_MAP+1l),equalTo("Not Found"));
    }

    @Test
    public void addItemWithHighRankNotToBeRemoved() throws Throwable {
        // given
        forgetMaPlease.add(LIMIT_MAP+1L,"aLowRankOne",0);
        //when
        forgetMaPlease.add(LIMIT_MAP+2L,"aHigherRankOne",10);
        forgetMaPlease.add(LIMIT_MAP+3L,"aMediumRankOne",5);
        // then
        assertThat("The key is not present anymore", forgetMaPlease.find(LIMIT_MAP+1l),equalTo("Not Found"));
        assertThat("The key with high rank is still there", forgetMaPlease.find(LIMIT_MAP+2l),equalTo("aHigherRankOne"));
    }

    @Test
    public void addItemAndRetrieveIt() throws  Throwable{
        forgetMaPlease.add(LIMIT_MAP+1L,"aValue",5);
        String myValue = forgetMaPlease.find(LIMIT_MAP+1L);
        assertThat(myValue,equalTo("aValue"));
    }

    @Test
    public void addItemOverLimitAndAssertElementSearchedIsNotRemoved() throws NotFoundException {


        forgetMaPlease.find(0L);
        forgetMaPlease.find(0L);
        forgetMaPlease.find(0L);

        forgetMaPlease.add(LIMIT_MAP+1L,"aValue",1);
        assertThat (forgetMaPlease.find(0L), equalTo("myValue0"));
    }

    private void fillForgetMap(int limit) {
        for (long num = 0; num < limit;  num++) {
            forgetMaPlease.add(num,"myValue" + num,new Random().nextInt(10));
        }
    }

}