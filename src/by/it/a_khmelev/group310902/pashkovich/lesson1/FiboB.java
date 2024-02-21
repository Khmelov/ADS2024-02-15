package by.it.a_khmelev.group310902.pashkovich.lesson1;

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
        BigInteger [] array = new BigInteger[n];
        BigInteger sum = BigInteger.ONE; // приравниваю сумму сразу к 1, т.к в цикле я не учитываю первую единицу

        array[0] = BigInteger.ZERO;
        array[1] = BigInteger.ONE;

        for(int i = 2; i < n; i++){
            array[i] = array[i-1].add(array[i-2]);
            sum = sum.add(array[i]);
        }

        return sum;
    }

}

