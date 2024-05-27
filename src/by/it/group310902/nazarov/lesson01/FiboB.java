package by.it.group310902.nazarov.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

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
//здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        if (n == 0)
            return BigInteger.ZERO;
        if (n == 1)
            return BigInteger.ONE;

        BigInteger pred = BigInteger.ZERO;
        BigInteger curr = BigInteger.ONE;
        BigInteger buff;
        for (int i = 1; i < n; i++) {
            buff = curr.add(pred);
            pred = curr;
            curr = buff;
        }
        return curr;
    }

}

