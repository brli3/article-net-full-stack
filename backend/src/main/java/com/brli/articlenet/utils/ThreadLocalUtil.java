package com.brli.articlenet.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // provide ThreadLocal object for each thread of request
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    /**
     * Get values saved in ThreadLocal
     * in this case it would be user information
     * @return
     * @param <T>
     */
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }

    /**
     * Set value
     * @param value value to be saved, in this case, claims from JWT
     */
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    /**
     * Remove value to avoid memory leak after request completes
     */
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
