package by.it.group351002.stepanenko.lesson01;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        // Вычисление остатка от деления n-го числа Фибоначчи на m с использованием периода Пизано
        // Время выполнения: O(m^2)
        int period = getPeriodLength(m); // Получаем период Пизано для заданного m
        int remainder = (int)(n % period); // Вычисляем остаток от деления n на период Пизано
        long[] fib = new long[period];
        fib[0] = 0;
        fib[1] = 1;

        for (int i = 2; i < period; i++) {
            fib[i] = (fib[i - 1] + fib[i - 2]) % m; // Вычисляем остаток от деления чисел Фибоначчи на m
        }

        return fib[remainder];
    }

    private int getPeriodLength(int m) {
        // Получение длины периода Пизано для заданного m
        int a = 0;
        int b = 1;
        int period = 0;

        for (int i = 0; i < m * m; i++) {
            int temp = a;
            a = b;
            b = (temp + b) % m;

            if (a == 0 && b == 1) {
                period = i + 1;
                break;
            }
        }

        return period;
    }
}
