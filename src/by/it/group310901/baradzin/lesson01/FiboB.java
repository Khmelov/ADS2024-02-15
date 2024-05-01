package by.it.group310901.baradzin.lesson01;

import java.math.BigInteger;

/**
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом без ограничений на размер
 * результата (BigInteger)
 */

public class FiboB {
    private final long startTime = System.currentTimeMillis();

    /**
     * Вычисление чисел простым быстрым методом
     */
    public static void main(String[] args) {
        var fibo = new FiboB();
        var n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Здесь нужно реализовать вариант с временем O(n) и памятью O(n)
     */
    BigInteger fastB(Integer n) {
        var arr = new BigInteger[n + 1];
        arr[0] = BigInteger.ZERO;
        arr[1] = BigInteger.ONE;
        for (var i = 2; i <= n; i++)
            arr[i] = arr[i - 2].add(arr[i - 1]);
        return arr[n];
    }
}
