package group351005.brezgunov.lesson01;

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
        BigInteger[] numbs = new BigInteger[n];
        numbs[0] = BigInteger.ONE;
        numbs[1] = BigInteger.TWO;
        n--;
        for (int i = 2; i < n; i++) {
            numbs[i] = numbs[i - 1].add(numbs[i - 2]);
        }
        return numbs[n - 1];
    }

}

