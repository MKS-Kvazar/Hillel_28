package mks;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerCounter {
    private final AtomicInteger counter;

    public IntegerCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void increment() {
        counter.incrementAndGet();
    }
}
