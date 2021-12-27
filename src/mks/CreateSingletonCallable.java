package mks;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CreateSingletonCallable implements Callable<SingletonClass> {
    final private CountDownLatch countDownLatch;
    final private int value;

    public CreateSingletonCallable(CountDownLatch countDownLatch, int value) {
        this.countDownLatch = countDownLatch;
        this.value = value;
    }

    @Override
    public SingletonClass call() throws InterruptedException {
        countDownLatch.await();
        return SingletonClass.newSingletonClass(value);
    }
}
