package top.meethigher;

import java.util.ArrayList;
import java.util.List;

public class CpuMulticoreTest {

    private static long[] preflight(String[] args) {
        long[] array = new long[2];
        try {
            array[0] = Long.parseLong(args[0]);
            array[1] = Long.parseLong(args[1]);
        } catch (Exception e) {
            array[0] = 6600_0000;
            array[1] = 1000_0000;
        }
        return array;
    }

    public static void main(String[] args) {
        long[] preflight = preflight(args);
        int numThreads = Runtime.getRuntime().availableProcessors();
//        long rangePerThread = 10_000_000; // 每个线程计算的范围
        long rangePerThread = preflight[1];
        System.out.println("it is detected that your machine has " + numThreads + " logical processors");
        System.out.println(numThreads + " threads will be started, and each thread will calculate " + rangePerThread + " primes");
        List<Thread> threads = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (long i = 0; i < numThreads; i++) {
            long startRange = preflight[0];
            long endRange = startRange + rangePerThread;
            Thread thread = new Thread(new PrimeCalculator(startRange, endRange));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("total time taken: " + (endTime - startTime) + " ms");
    }
}

/**
 * 素数（Prime Number）是指在大于1的自然数中，只能被1和其本身整除的数。
 *
 * @author chenchuancheng
 * @since 2024/06/02 09:30
 */
class PrimeCalculator implements Runnable {
    private long startRange;
    private long endRange;

    public PrimeCalculator(long startRange, long endRange) {
        this.startRange = startRange;
        this.endRange = endRange;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (long i = startRange; i < endRange; i++) {
            if (isPrime(i)) {
                // 如果需要，可以在这里对素数进行处理，例如计数
            }
        }
        System.out.println("thread " + Thread.currentThread().getName() + " took " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 判断是否为素数
     * 素数（Prime Number）是指在大于1的自然数中，只能被1和其本身整除的数。
     *
     * @param number 传入一个数值
     * @return true表示素数
     */
    private boolean isPrime(long number) {
        //素数定义为大于1的自然数。
        if (number <= 1) {
            return false;
        }
        //假如a*b=number，如果a和b都大于number的平方根，则a*b>number，故i<=Math.sqrt(number)
        for (long i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
