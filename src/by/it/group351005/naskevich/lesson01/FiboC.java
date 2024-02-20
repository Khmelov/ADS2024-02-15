package by.it.group351005.naskevich.lesson01;

import java.util.ArrayList;

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
        int n = 21;
        int m = 4;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        if (n < 2) {
            return n;
        }

        ArrayList<Long> period = new ArrayList<>();
        period.add(0l);
        period.add(1l);

        int i = 2;
        int periodPos = 0;
        int periodEnd = 1;
        long nextFib = 0;
        while(periodPos != periodEnd && i <= n) {
            nextFib = (period.get(i - 1) + period.get(i - 2)) % m;
            period.add(nextFib);

            if (nextFib == period.get(periodPos))
                periodPos++;
            else {
                periodPos = 0;
                periodEnd = i;
            }

            i++;
        }

        if (i > n)
            return nextFib;

        return period.get((int)(n % (periodEnd + 1)));
    }


}

