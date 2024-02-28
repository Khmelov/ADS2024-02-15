package by.it.group310901.baradzin.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {
    private final long startTime = System.currentTimeMillis();

    // Вычисление чисел простым быстрым методом
    public static void main(String[] args) {
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    // Здесь нужно реализовать вариант с временем O(n) и памятью O(n)
    BigInteger fastB(Integer n) {
        BigInteger[] allNums = new BigInteger[n];
        if (n == 1) return BigInteger.ONE;
        if (n == 0) return BigInteger.ZERO;
        allNums[0]=BigInteger.ZERO;
        allNums[1]=BigInteger.ONE;
        for(int i = 2; i<allNums.length; i++){
            allNums[i] = allNums[i-1].add(allNums[i-2]);
        }
        return allNums[n-1].add(allNums[n-2]);
    }
}
