package org.dibek;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by giuseppe.dibella on 18/12/2016.
 * A wrapper for the objecs contained in the map.
 * This class permit to assign to the item a rank value that is used to decide which object will be removed from the map
 * when the max limit is reached.
 *
 */
public class ForgetMeWrapper<T> implements Rankable<T>, Comparable<ForgetMeWrapper<T>>{


    public static final long DEFAULT_ID = -1l;
    private Long id;

    private Integer rank;

    private T value;

    private AtomicInteger rankCounter;

    private  int limitRank ;






    private ForgetMeWrapper(ForgetMeWrapperBuilder<T> forgetMeWrapperBuilder) {
        this.id = forgetMeWrapperBuilder.id;
        this.rank = forgetMeWrapperBuilder.rank;
        this.value = forgetMeWrapperBuilder.value;
        this.limitRank = forgetMeWrapperBuilder.limitRank;
        this.rankCounter = new AtomicInteger(rank);
    }





    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Integer getRank() {
        return this.rank;
    }

    @Override
    public T getValueAndIncreaseRank() {
        this.increaseRank();
        return this.value;
    }

    /**
     * A static method to create a default value used when a find doesn't return any value.
     * @param valueDefault the value of the default item.
     * @return A default ForgetMeWrapper
     */
    public static ForgetMeWrapper createDefault(Object valueDefault) {
        return  new ForgetMeWrapperBuilder().id(DEFAULT_ID).rank(0).limitRank(10).value(valueDefault).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForgetMeWrapper<?> that = (ForgetMeWrapper<?>) o;

        if (!id.equals(that.id)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }


    private void increaseRank() {
        if (this.rank < this.limitRank) {
            this.rank = rankCounter.incrementAndGet();
        }
    }



    @Override
    public int compareTo(ForgetMeWrapper<T> o) {
        int rankCompare = Integer.compare(this.getRank(), o.getRank());
        return rankCompare == 0? Long.compare(this.getId(),o.getId()) : rankCompare;
    }

    /**
     * A builder to simplify the creation of the wrapper.
     * @param <T>
     */
    public static class ForgetMeWrapperBuilder<T> {
        private Long id;
        private Integer rank;
        private T value;
        private int limitRank;


        public ForgetMeWrapperBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ForgetMeWrapperBuilder rank(Integer rank) {
            this.rank = rank;
            return this;
        }

        public ForgetMeWrapperBuilder value(T value) {
            this.value = value;
            return this;
        }
        public ForgetMeWrapperBuilder limitRank(int limitRank) {
            this.limitRank = limitRank;
            return this;
        }

        public ForgetMeWrapper<T> build() {
            return new ForgetMeWrapper<T>(this);
        }

    }

}
