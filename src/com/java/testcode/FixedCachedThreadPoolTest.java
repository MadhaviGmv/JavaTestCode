package com.java.testcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * Using cached thread pool creates new threads as and when needed and re-uses if older threads are available.
 * Fixed thread pool creates a fixed number of threads and once all are busy, new tasks will wait in a queue.
 * Prints output from different threads started from main thread. Main thread waits till child threads die.
 */
public class FixedCachedThreadPoolTest {
    public static void main(String[] args) throws Exception {
        ExecutorService threadpoolExec = Executors.newCachedThreadPool();
//         ExecutorService threadpoolExec = Executors.newFixedThreadPool(4); 

        long timeIn = System.currentTimeMillis();
        int count = 1000;
        while (count-- > 0)
            threadpoolExec.submit(new SleeperTask());

        threadpoolExec.shutdown();
        threadpoolExec.awaitTermination(1000, TimeUnit.SECONDS);
        long timeOut = System.currentTimeMillis();

        System.out.println(timeOut - timeIn);
    }

    private static class SleeperTask implements Runnable {
        public void run() {
            try {
                Thread.sleep(1000); // This is to simulate Short-lived and
                                    // asynchronous tasks. 
                System.out.println("Done with sleep, Bye. Id : "+Thread.currentThread().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
