package by.it.group351001.gavrilovich.lesson01;

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

        if (n == 0)
        {
            return BigInteger.ZERO;
        }
        else if (n == 1)
        {
            return BigInteger.ONE;
        }
        else
        {
            BigInteger[] number = new BigInteger[n];
            number[0] = BigInteger.valueOf(0);
            number[1] = BigInteger.valueOf(1);

            int i;

            for (i = 2; i < n; i++) {
                number[i] = number[i - 1].add(number[i - 2]);
            }


            //здесь нужно реализовать вариант с временем O(n) и памятью O(n)

            return number[i - 1].add(number[i - 2]);
        }
    }

}

