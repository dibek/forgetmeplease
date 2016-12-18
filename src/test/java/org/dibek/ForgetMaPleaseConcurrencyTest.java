package org.dibek;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

/**
 * Created by giuseppe.dibella on 16/12/2016.
 */
public class ForgetMaPleaseConcurrencyTest {


    final static int LIMIT_FORGET = 400;


    Executor exec;

    final static int NUMBER_THREADS = 100;

    final static int NUMBER_ADDERS = 1000;

    final static int NUMBER_FINDERS = 10000;



    final static int NUMBER_ADDS = 30;

    CountDownLatch latch;

    @Before
    public void setUp() throws Exception {
        exec = Executors.newFixedThreadPool(NUMBER_THREADS);
        latch = new CountDownLatch(NUMBER_ADDERS);
    }

    @Test
    public void differentThreadsAddingValuesNotChangeMaxSize() {
        ForgetMaPlease<String> forgetMaPlease = new ForgetMaPlease.ForgetMePleaseBuilder()
                .size(LIMIT_FORGET)
                .defaultValue("Not Found")
                .limitRank(10).build();
        Runnable[] lotOfAddersAndFinders = createAddersAndFinders(NUMBER_ADDERS,NUMBER_FINDERS, forgetMaPlease, latch);
       for (Runnable myRunnable : lotOfAddersAndFinders) {
           exec.execute(myRunnable);
       }
        try {
            latch.await();
        } catch (InterruptedException ignored) {
        } finally {
            System.out.println(forgetMaPlease.size() + " is the map size");
           assertTrue(forgetMaPlease.size() > 0 && forgetMaPlease.size() <= LIMIT_FORGET);
        }

    }

    @Test
    public void differentThreadsAddingValuesNotChangeMaxSizeUsingDifferentObject() {
        ForgetMaPlease<BigInteger> forgetMaPlease = new ForgetMaPlease.ForgetMePleaseBuilder<BigInteger>()
                .size(LIMIT_FORGET)
                .defaultValue(BigInteger.valueOf(-1000))
                .limitRank(10).build();
        Runnable[] lotOfAddersAndFinders = createAddersAndFinders(NUMBER_ADDERS,NUMBER_FINDERS, forgetMaPlease, latch);
        for (Runnable myRunnable : lotOfAddersAndFinders) {
            exec.execute(myRunnable);
        }
        try {
            latch.await();
        } catch (InterruptedException ignored) {
        } finally {
            System.out.println(forgetMaPlease.size() + " is the map size");
            assertTrue(forgetMaPlease.size() > 0 && forgetMaPlease.size() <= LIMIT_FORGET);
        }

    }

    @Test
    public void differentThreadsAddingValuesNoFinders() {
        ForgetMaPlease<BigInteger> forgetMaPlease = new ForgetMaPlease.ForgetMePleaseBuilder<BigInteger>()
                .size(LIMIT_FORGET)
                .defaultValue(BigInteger.valueOf(-1000))
                .limitRank(10).build();
        Runnable[] lotOfAddersAndFinders = createAddersAndFinders(NUMBER_ADDERS,0, forgetMaPlease, latch);
        for (Runnable myRunnable : lotOfAddersAndFinders) {
            exec.execute(myRunnable);
        }
        try {
            latch.await();
        } catch (InterruptedException ignored) {
        } finally {
            System.out.println(forgetMaPlease.size() + " is the map size");
            assertTrue(forgetMaPlease.size() > 0 && forgetMaPlease.size() <= LIMIT_FORGET);
        }

    }

    private Runnable adderAndCounter(ForgetMaPlease forgetMaPlease, CountDownLatch countDownLatch) {
        return () -> {
            //long key = Math.abs(new Random().nextLong());
            long key = countDownLatch.getCount();
            int countMap = forgetMaPlease.add(key, "testValue" + key,new Random().nextInt(10));
            if (countMap > LIMIT_FORGET ) {
                System.out.println("Size ("+countMap+") for map is wrong when adding: testValue" + key);
            }
            countDownLatch.countDown();


        };

    }

    private Runnable finders(ForgetMaPlease forgetMaPlease, CountDownLatch countDownLatch) {
        return () -> {
            long randomKey = new Random().nextInt(NUMBER_ADDERS);
            Object object = forgetMaPlease.find(randomKey);

        };
    }

    private Runnable[] createAddersAndFinders(int numAdders, int numFinders, ForgetMaPlease forgetMaPlease, CountDownLatch countDownLatch) {
        Runnable[] addersAndFinders = new Runnable[numAdders + numFinders];
        for (int i = 0; i < numAdders; i++) addersAndFinders[i] = adderAndCounter(forgetMaPlease,countDownLatch);
        for (int i = numAdders; i < numFinders + numAdders; i++) addersAndFinders[i] = finders(forgetMaPlease,countDownLatch);
        return addersAndFinders;
    }
}
