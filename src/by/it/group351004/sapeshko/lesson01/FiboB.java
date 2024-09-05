package by.it.group351004.sapeshko.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи со вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        //вычисление чисел простым быстрым методом
        by.it.group351004.sapeshko.lesson01.FiboB fibo = new by.it.group351004.sapeshko.lesson01.FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
        BigInteger[] fibo = new BigInteger [n+1];
        fibo[0] = BigInteger.ZERO;
        fibo[1] = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            fibo[i] = fibo[i-1].add(fibo[i-2]);
        }

        return fibo[n];
    }

}

