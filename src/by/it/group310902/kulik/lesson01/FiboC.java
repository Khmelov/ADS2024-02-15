package by.it.group310902.kulik.lesson01;

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
        long pisanoPeriod = getPisanoPeriod(m);
        long remainder = n % pisanoPeriod;

        long[] fibMod = new long[(int) (pisanoPeriod + 1)];
        fibMod[0] = 0;
        fibMod[1] = 1;

        for (int i = 2; i <= pisanoPeriod; i++) {
            fibMod[i] = (fibMod[i - 1] + fibMod[i - 2]) % m;
        }

        return fibMod[(int) remainder];
    }

    long getPisanoPeriod(long m) {
        long a = 0, b = 1, c = a + b;
        for (long i = 0; i < m * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1) return i + 1;
        }
        return -1;
    }



}

