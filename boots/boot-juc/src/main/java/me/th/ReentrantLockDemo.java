package me.th;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                phone.sale();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "小王").start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                phone.sale();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "小张").start();
    }
}

class Phone {

    private int total = 50;

    private final Lock lock = new ReentrantLock();

    public void sale() {
        try {
            lock.lock();
            if (total > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了一部手机，当前库存剩余：" + (--total));
            }
        } finally {
            lock.unlock();
        }
    }
}