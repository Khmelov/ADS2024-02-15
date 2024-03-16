package by.it.group310901.chizh.lesson01;

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
        BigInteger[] lst;
        lst = new BigInteger[n + 1];
        lst[0] = BigInteger.valueOf(0);
        if (n > 0){
            lst[1] = BigInteger.valueOf(1);
        }
        for (int i = 2; i < n + 1; i++){
            lst[i] = lst[i - 2].add(lst[i - 1]);
        }
        return lst[n];
    }

}

