package by.it.group351003.kalinckovich.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
        BigInteger[] a = new  BigInteger[5];
        a[0] = BigInteger.ONE;
        a[1] = BigInteger.ZERO;
        a[3] = BigInteger.ZERO;
        a[4] = BigInteger.ZERO;
        for(int i = 3;i < n;i++){
            a[1] = a[3];
            a[4]  = a[4].add(a[1].add(a[0]));
            a[3] = a[0];
            a[0] = a[1].add(a[0]);
        }
        a[4] = a[4].add(BigInteger.valueOf(2));
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        return a[4];
    }

}

