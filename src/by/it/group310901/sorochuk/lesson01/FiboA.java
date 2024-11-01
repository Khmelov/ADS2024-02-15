package by.it.group310901.sorochuk.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить рекурсивный способ вычисления чисел Фибоначчи
 */

public class FiboA {


    private long startTime = System.currentTimeMillis();
    //Это точка входа в программу.
//В методе создается экземпляр класса FiboA и вызываются методы calc и slowA.
//Значения n задаются вручную (33 и 34) для демонстрации вычислений.
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
    //Этот метод вычисляет число Фибоначчи с использованием простого рекурсивного подхода.
    private int calc(int n) {
        //здесь простейший вариант, в котором код совпадает
        //с математическим определением чисел Фибоначчи
        //время O(2^n)
        if (n == 0 || n == 1) return n;
        return calc(n-1) + calc(n - 2);
    }

    //Этот метод также вычисляет число Фибоначчи с использованием рекурсии,
// но использует объект класса BigInteger
    BigInteger slowA(Integer n) {
        //рекурсия
        //здесь нужно реализовать вариант без ограничения на размер числа,
        //в котором код совпадает с математическим определением чисел Фибоначчи
        if (n.equals(0)) return BigInteger.ZERO;
        if (n.equals(1)) return BigInteger.ONE;

        return slowA(n - 1).add(slowA(n-2));
    }


}

