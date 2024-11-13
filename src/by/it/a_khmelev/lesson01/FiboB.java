package by.it.a_khmelev.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи со вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    BigInteger fastB(Integer n) {


        BigInteger[] FiboArr = new BigInteger[n];
        FiboArr[0] = BigInteger.ONE;
        FiboArr[1] = BigInteger.ONE;
        for (int i = 2; i < n; i++)
            FiboArr[i] = FiboArr[i - 1].add(FiboArr[i - 2]);
        return FiboArr[n - 1];
=======
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        return BigInteger.valueOf(-1L);
      
    }

}

