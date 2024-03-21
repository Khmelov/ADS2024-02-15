package by.it.group351001.strizhak.lesson01;

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
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        int i = 3;
        BigInteger sum = a.add(b);
        while (i <= n) {
            a = b;
            b = sum;
            sum = a.add(b);
            i++;
        }
        if (n>0) {return sum;}
        return BigInteger.ZERO;
    }

}

