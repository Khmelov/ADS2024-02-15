package by.it.group351003.gornik_artur.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private final long  startTime = System.currentTimeMillis();

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
        BigInteger[] nums = new BigInteger[n+1];
        if (n == 1) return BigInteger.ZERO;
        else if (n == 2) return BigInteger.ONE;
        else {
            nums[0] = BigInteger.ZERO;
            nums[1] = BigInteger.ONE;
            for (int i = 2; i < nums.length; i++) {
                nums[i] = nums[i - 1].add(nums[i - 2]);
            }
            return nums[n];
        }
    }

}

