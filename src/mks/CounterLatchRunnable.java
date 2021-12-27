package mks;

import java.util.concurrent.CountDownLatch;

public class CounterLatchRunnable implements Runnable {
    final private CountDownLatch countDownLatch;
    final private IntegerCounter counter;

    public CounterLatchRunnable(CountDownLatch countDownLatch, IntegerCounter counter) {
        this.countDownLatch = countDownLatch;
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.increment();
        countDownLatch.countDown();
    }
}
