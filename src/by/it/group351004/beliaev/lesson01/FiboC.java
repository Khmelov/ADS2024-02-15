package by.it.group351004.beliaev.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        int pisanoPeriod = getPisanoPeriod(m);

        // уменьшение n
        n %= pisanoPeriod;

        long[] fiboArray = new long[pisanoPeriod];
        fiboArray[0] = 0L;
        fiboArray[1] = 1L;

        for (int i = 2; i <= n; i++) {
            fiboArray[i] = (fiboArray[i - 1] + fiboArray[i - 2]) % m;
        }

        return fiboArray[(int) n];
    }

    int getPisanoPeriod(int m) {
        long previous = 0L;
        long current = 1L;

        for (int i = 0; i < m * m; i++) {
            long temp = (previous + current) % m;
            previous = current;
            current = temp;

            if (previous == 0L && current == 1L) {
                return i + 1;
            }
        }

        return 0;
    }


}

