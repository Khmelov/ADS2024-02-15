package by.it.group351002.ilian_buhovets.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;

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

        int[] a = new int[m*4];
        a[0]=0;
        a[1]=1;
        int i = 1;

        do {
            i++;
            a[i] = (a[i - 1] + a[i - 2]) % m;
        } while (a[i] != 1 || a[i - 1] != 0);

        i = (int) n%i;

        return a[i];
    }


}

