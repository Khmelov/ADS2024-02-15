package by.it.group310901.brylev.lesson01;

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


    private int findPisano(int m)
    {
        int a = 0, b = 1, c;
        for (int i = 0; i < m * 6; i++)
        {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1)
            {
                return i + 1;
            }
        }
        return m*m;
    }


    long fasterC(long n, int m)
    {
        int period = findPisano(m);
        n %= period;
        long[] fibo = new long[(int)(n + 1)];
        fibo[0] = 0;
        if (n > 0)
        {
            fibo[1] = 1;
            for (int i = 2; i <= n; i++)
            {
                fibo[i] = (fibo[i - 1] + fibo[i - 2]) % m;
            }
        }
        return fibo[(int) n];
    }
}

