package org.dibek;

import org.dibek.exception.NotFoundException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ForgetMePlease<T> {


    public static final String NOTFOUNDITEM = "ITEMNOTFOUND" ;
    
    private ConcurrentHashMap<Integer,T> fixedSizeMap;
    
    private AtomicInteger maxSize;

    private AtomicInteger keyGenerator = new AtomicInteger();


    public ForgetMePlease(int size) {
        this.fixedSizeMap = new ConcurrentHashMap<>() ;
        this.maxSize = new AtomicInteger(size);
    }

    public int add(int key, T value) {


        this.fixedSizeMap.computeIfAbsent(key, f -> {
            if (fixedSizeMap.size() == this.maxSize.get()) {
                int n = 0;
                do {
                    System.out.print(n);
                }
                while (fixedSizeMap.remove(n++) == null);
            }
            return value;
        }
        );
        this.fixedSizeMap.put(key,value);
        return this.fixedSizeMap.size();

    }




    public T find(Integer key) throws NotFoundException {
        if (this.fixedSizeMap.get(key) != null) {
            return this.fixedSizeMap.get(key);
        }
        else {
            throw new NotFoundException("Item with key " + key + " not found");
        }
    }
}
