package by.it.group310902.sverchkov.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить рекурсивный способ вычисления чисел Фибоначчи
 */

public class FiboA {


    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboA fibo = new FiboA();
        int n = 33;
        System.out.printf("calc(%d)=%d \n\t time=%d \n\n", n, fibo.calc(n), fibo.time());

        //вычисление чисел фибоначчи медленным методом (рекурсией)
        fibo = new FiboA();
        n = 34;
        System.out.printf("slowA(%d)=%d \n\t time=%d \n\n", n, fibo.slowA(n), fibo.time());
    }

    private long time() {
        long res = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        return res;
    }

    //здесь простейший вариант, в котором код совпадает
    //с математическим определением чисел Фибоначчи
    //время O(2^n)
    private int calc(int n) {

        // простейший вариант без рекурсии для небольших чисел
        // int cur = 1, prev = 0, i = 2;
        // while (i < n) {
        //     int temp = cur;
        //     cur += prev;
        //     prev = temp;
        //     i++;
        // }

        //рекурсия
        return (n == 1 || n == 2) ? n-1 : calc(n - 1) + calc(n - 2);
    }

    //рекурсия
    //здесь нужно реализовать вариант без ограничения на размер числа,
    //в котором код совпадает с математическим определением чисел Фибоначчи
    //время O(2^n)
    BigInteger slowA(Integer n) {
        if (n == 1) return BigInteger.ZERO;
        if (n == 2) return BigInteger.ONE;
        return slowA(n - 1).add(slowA(n - 2));
    }
}

