package by.it.group310902.belskiy.lesson01.lesson01;



/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FibC {

    private final long  startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FibC fibo = new FibC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        if (n <= 0 || m <= 1) {
            return 0;
        }

        int period = findPeriod(m); // Находим период Пизано для m

        // Вычисляем остаток от деления n на период Пизано
        long remainder = n % period;

        long[] fibModM = new long[period];
        fibModM[0] = 0;
        fibModM[1] = 1;

        // Заполняем массив fibModM с остатками чисел Фибоначчи по модулю m
        for (int i = 2; i < period; i++) {
            fibModM[i] = (fibModM[i - 1] + fibModM[i - 2]) % m;
        }

        return fibModM[(int)remainder];
    }

    public static int findPeriod(int m) {
        int a = 0;
        int b = 1;
        int period = 0;

        // Поиск периода Пизано
        for (int i = 0; i < m * m; i++) {
            int temp = (a + b) % m;
            a = b;
            b = temp;

            if (a == 0 && b == 1) {
                period = i + 1;
                break;
            }
        }

        return period;
    }


}

