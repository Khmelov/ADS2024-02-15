package by.it.group351003.kolbeko.lesson01;

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
        if (n == 1)
            return BigInteger.ZERO;
        else if (n == 2)
            return BigInteger.ONE;
        else {
            BigInteger[] fiboArr = new BigInteger[n + 1];
            fiboArr[0] = BigInteger.ZERO;
            fiboArr[1] = BigInteger.ONE;
            for (int i = 2; i < fiboArr.length; i++) {
                fiboArr[i] = fiboArr[i - 1].add(fiboArr[i - 2]);
            }
            return fiboArr[n];
        }
    }

}

