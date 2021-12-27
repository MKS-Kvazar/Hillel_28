package mks;

import java.util.concurrent.CountDownLatch;

public class Task4bRunnable implements Runnable {
    final private CountDownLatch countDownLatch;
    final private Object lock_1;
    final private Object lock_2;

    public Task4bRunnable(CountDownLatch countDownLatch, Object lock_1, Object lock_2) {
        this.countDownLatch = countDownLatch;
        this.lock_1 = lock_1;
        this.lock_2 = lock_2;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock_2) {
            System.out.println("Вход lock_2 потока Task4bRunnable");
            synchronized (lock_1) {
                System.out.println("Вход lock_1 потока Task4bRunnable");
            }
            System.out.println("Выход из lock_1 потока Task4bRunnable");
        }
        System.out.println("Выход из lock_2 потока Task4bRunnable");
    }
}

