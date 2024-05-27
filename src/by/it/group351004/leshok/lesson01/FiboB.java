package by.it.group351004.leshok.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

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
        if (n == 2 || n == 1)
            return BigInteger.ONE;

        BigInteger[] A = new BigInteger[3];
        A[0] = BigInteger.ONE;
        A[1] = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            A[2] = A[1].add(A[0]);
            A[0] = A[1];
            A[1] = A[2];
        }
        return A[2];
    }

}