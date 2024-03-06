package com.brli.articlenet.utils;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalGetAndSet() {
        ThreadLocal<String> tl = new ThreadLocal<>();

        // start two threads
        new Thread(() -> {
            tl.set("Tom");
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            }
        }, "blue").start();

        new Thread(() -> {
            tl.set("Jerry");
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s: %s\n", Thread.currentThread().getName(), tl.get());
            }
        }, "yellow").start();

    }
}
