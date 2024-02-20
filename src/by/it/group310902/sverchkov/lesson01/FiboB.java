package by.it.group310902.sverchkov.lesson01;

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
        int n = 500;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)

        ArrayList<BigInteger> fiboList = new ArrayList<>();
        fiboList.addFirst(BigInteger.valueOf(1));
        fiboList.add(1,BigInteger.valueOf(1));
        for (int i = 2; i < n; i++) {
            fiboList.add(i,fiboList.get(i-1).add(fiboList.get(i-2)));
        }
        return fiboList.get(n-1);
    }

}

