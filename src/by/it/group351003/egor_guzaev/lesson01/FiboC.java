package by.it.group351003.egor_guzaev.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * Время расчета должно быть не более 2 секунд
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
        // Период пизано
        int pisanoPeriod = getPisanoPeriod(m);
        n = n % pisanoPeriod;

        long previous = 0;
        long current  = 1;

        for (int i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % m;
        }

        return current % m;
    }

    int getPisanoPeriod(int m) {
        int previous = 0;
        int current  = 1;
        int res = 0;

        for(int i = 0; i < m * m; i++) {
            int temp = previous;
            previous = current;
            current = (temp + current) % m;

            // A Pisano Period starts with 01
            if (previous == 0 && current == 1) {
                res = i + 1;
                break;
            }
        }
        return res;
    }

}

