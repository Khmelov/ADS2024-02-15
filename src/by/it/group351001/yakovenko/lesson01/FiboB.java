package by.it.group351001.yakovenko.lesson01;

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
        int n = 55555 ;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    private BigInteger []Mas = {BigInteger.ZERO,BigInteger.ONE};
    private  BigInteger Temp = BigInteger.ZERO;
    BigInteger fastB(Integer n) {
        for (int i = 2; i <= n ; i++) {
            Temp = Mas[0].add(Mas[1]);
            Mas[0] = Mas[1];
            Mas[1] = Temp;
        }
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        return Temp;
    }

}

