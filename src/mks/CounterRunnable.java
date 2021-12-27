package mks;

public class CounterRunnable implements Runnable {
    final private IntegerCounter counter;

    public CounterRunnable(IntegerCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.increment();
    }
}
