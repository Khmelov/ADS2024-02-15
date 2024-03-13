package by.it.group351002.stepanenko.lesson01;

import java.math.BigInteger;

public class FiboA {

    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboA fibo = new FiboA();
        int n = 33;
        System.out.printf("calc(%d)=%d \n\t time=%d \n\n", n, fibo.calc(n), fibo.time());

        // Вычисление чисел Фибоначчи с помощью медленного метода (рекурсия)
        fibo = new FiboA();
        n = 34;
        System.out.printf("slowA(%d)=%d \n\t time=%d \n\n", n, fibo.slowA(n), fibo.time());
    }

    private long time() {
        long res = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        return res;
    }

    private int calc(int n) {
        // Простейший вариант вычисления чисел Фибоначчи
        // Основан на математическом определении чисел Фибоначчи
        // Время выполнения: O(2^n)
        if (n <= 1) {
            return n;
        } else {
            return calc(n - 1) + calc(n - 2);
        }
    }

    BigInteger slowA(Integer n) {
        // Рекурсивный метод вычисления чисел Фибоначчи без ограничения на размер числа
        // Основан на математическом определении чисел Фибоначчи
        // Время выполнения: O(2^n)
        if (n <= 1) {
            return BigInteger.valueOf(n);
        } else {
            return slowA(n - 1).add(slowA(n - 2));
        }
    }
}
