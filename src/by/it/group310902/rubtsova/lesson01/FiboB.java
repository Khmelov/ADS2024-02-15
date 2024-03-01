package by.it.group310902.rubtsova.lesson01;

import java.math.BigInteger;


public class FiboB {

    private final long startTime = System.currentTimeMillis();

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
        BigInteger [] a = new BigInteger[n + 1];
        a[0] = BigInteger.ZERO;
        a[1] = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            a[i] = a[i - 2].add(a[i - 1]);
        }
        return a[n];
    }

}

