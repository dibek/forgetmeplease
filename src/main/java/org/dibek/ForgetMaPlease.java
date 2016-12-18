package org.dibek;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A container of generic Maps with key type always Long but with values of different types
 * The Map can contain a limited number of associations and when the limit is reached the elements with the lower
 * rank are removed.
 * The rank is increased in the Wrapper Class every time a key is requested to the map.
 * @param <T> The type of the object value contained in the internal map
 */
public class ForgetMaPlease<T> {



    private ConcurrentHashMap<Long,ForgetMeWrapper> fixedSizeMap;

    private ConcurrentSkipListSet<ForgetMeWrapper<T>> skipListSet;
    
    private AtomicInteger maxSize;

    private ForgetMeWrapper defaultNotFoundValue;

    private int limitRank;


    private ForgetMaPlease(ForgetMePleaseBuilder forgetMePleaseBuilder) {
        this.fixedSizeMap = new ConcurrentHashMap<>() ;
        this.skipListSet = new ConcurrentSkipListSet<>();
        this.maxSize = new AtomicInteger(forgetMePleaseBuilder.size);
        this.limitRank = forgetMePleaseBuilder.limitRank;
        this.defaultNotFoundValue =  ForgetMeWrapper.createDefault(forgetMePleaseBuilder.defaultValue);
    }

    public int add(Long key, T value) {
        return this.add(key, value, 0);
    }


    public synchronized  int add(Long key, T value, int rank) {
        this.fixedSizeMap.computeIfAbsent(key, f -> {
                    final ForgetMeWrapper<T> forgetMeWrapper = new ForgetMeWrapper.ForgetMeWrapperBuilder()
                            .id(key)
                            .rank(rank)
                            .limitRank(this.limitRank)
                            .value(value).build();

            if (fixedSizeMap.size() >= this.maxSize.get()) {
                try {
                        fixedSizeMap.remove(this.skipListSet.first().getId());
                        this.skipListSet.remove(this.skipListSet.first());
                }
                catch (NoSuchElementException ex) {
                    System.out.println("Item not found");
                }

            }
            this.skipListSet.add(forgetMeWrapper);
            return forgetMeWrapper;
        }
        );
        return this.fixedSizeMap.size();

    }


    /**
     * A finder for the elements contained in the map.
     * It uses the key to extract the corrispondent value.
     * For every extraction the rank of the object is increased by one till a max limit.
     * @param key The key of the object saved in the map.
     * @return The object saved in the map or a default value in case of object not found.
     */
    public T find(Long key)  {
        T value = (T) this.fixedSizeMap.getOrDefault(key, this.defaultNotFoundValue).getValueAndIncreaseRank();

        return value;
     }

    /**
     *
     * @return The size of the map.
     *
     */
    public int size() {
        return this.fixedSizeMap.size();
    }


    /** A builder  to simplify the creation of the ForgetMaPlease class
     *
     * @param <T> The type of the value managed by the Forgetting map.
     */
    public static class ForgetMePleaseBuilder<T> {

        int size;
        T defaultValue;
        int limitRank;

        public ForgetMePleaseBuilder size(int size) {
            this.size = size;
            return this;
        }
        public ForgetMePleaseBuilder defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }
        public ForgetMePleaseBuilder limitRank(int limitRank) {
            this.limitRank = limitRank;
            return this;
        }
        public ForgetMaPlease<T> build() {
            return new ForgetMaPlease<T>(this);
        }


    }

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name
        System.out.print("Enter the size of the map: ");

        String sizeMap = scanner.next();

        ForgetMaPlease<String> forgetMaPlease =  new ForgetMePleaseBuilder()
                .size(Integer.valueOf(sizeMap))
                .defaultValue("NotFound")
                .limitRank(20)
                .build();




        System.out.print("Add a word or a list of words (without spaces and comma separated) to the map: ");

        String itemToInsert = scanner.next();

        String[] words = itemToInsert.replaceAll(";",",").split(",");

        long count = 0;
        for (String word : words) {
            System.out.println("Size map  after inserting " + word  + " is " + forgetMaPlease.add(count, word));
            count++;
        }

        System.out.print("Try to find something adding a number lower than " + forgetMaPlease.size() + ": ");

        String key = scanner.next();

        System.out.println("Result : " + forgetMaPlease.find(Long.valueOf(key)));

    }
}
