package org.dibek;

/**
 * Created by giuseppe.dibella on 18/12/2016.
 */
public  interface Rankable<T> {

    Long getId();
    Integer getRank();
    T getValueAndIncreaseRank();


}
