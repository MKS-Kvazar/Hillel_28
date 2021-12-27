package mks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        new Main().Task_1();
        new Main().Task_2();
        new Main().Task_3();
        new Main().Task_4();
    }

    private void Task_1() {
//        С помощью 'volatile' решить броблему не удалось
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
        final int size = 2_000;
        CountDownLatch startSignal = new CountDownLatch(size);
        IntegerCounter counter = new IntegerCounter(new AtomicInteger(0));
        Runnable task = new CounterLatchRunnable(startSignal, counter);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int index = 0; index < size; index++) {
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

    private void Task_3() {
//        Решил проблему добавив 'synchronized' в метод SingletonClass.newSingletonClass(value)
        final int size = 10_000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<SingletonClass>> futureList = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            futureList.add(service.submit(new CreateSingletonCallable(countDownLatch, index)));
        }
        countDownLatch.countDown();
        Set<SingletonClass> integers = new HashSet<>();
        try {
            for (Future<?> future : futureList) {
                SingletonClass item = (SingletonClass) future.get();
                integers.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        for (SingletonClass x : integers) {
            System.out.println(x);
        }
        System.out.println("Finish Task 3");
    }

    private void Task_4() {
        System.out.println("Start Task 4");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Object lock_1 = new Object();
        Object lock_2 = new Object();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Task4aRunnable(countDownLatch, lock_1, lock_2));
        service.execute(new Task4bRunnable(countDownLatch, lock_1, lock_2));
        countDownLatch.countDown();
        service.shutdown();
    }
}
