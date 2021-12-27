package mks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        new Main().Task_1();
        new Main().Task_2();
    }

    private void Task_1() {
        IntegerCounter counter = new IntegerCounter(new AtomicInteger(0));
        Runnable task = new CounterRunnable(counter);
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<?>> futureList = new ArrayList<>();
        for (int index = 0; index < 2_000; index++) {
            futureList.add(service.submit(task));
        }
        try {
            for (Future<?> future : futureList) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        System.out.println(counter.getCounter());
    }

    private void Task_2() {
        CountDownLatch startSignal = new CountDownLatch(2000);
        IntegerCounter counter = new IntegerCounter(new AtomicInteger(0));
        Runnable task = new CounterLatchRunnable(startSignal, counter);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int index = 0; index < 2_000; index++) {
            service.execute(task);
        }
        try {
            startSignal.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        System.out.println(counter.getCounter());
    }
}
