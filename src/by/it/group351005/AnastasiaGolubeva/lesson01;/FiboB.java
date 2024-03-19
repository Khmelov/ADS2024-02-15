package by.it.group351005.AnastasiaGolubeva.lesson01;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

import java.math.BigInteger;

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
        if (n == 2 || n == 1)
            return BigInteger.ONE;
      /*  BigInteger[] F = new BigInteger [n];
        F[0] = BigInteger.ONE;
        F[1] = BigInteger.ONE;
        for(int i = 2; i < n; i++) {
          F[i] = F[i - 1].add(F[ i - 2]);
*/

        BigInteger[] F = new BigInteger [3];
        F[0] = BigInteger.ONE;
        F[1] = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            F[2] = F[1].add(F[0]);
            F[0] = F[1];
            F[1] = F[2];
        }
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        return new BigInteger(String.valueOf(F[2]));
    }

}

