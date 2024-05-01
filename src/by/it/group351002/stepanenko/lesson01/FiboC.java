package by.it.group351002.stepanenko.lesson01;

import java.util.ArrayList;
import java.util.List;

public class FiboC {
    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 55555;
        int m = 1000;
        System.out.printf("fasterC(%d)=%d\n", n, fibo.fasterC(n, m));
    }

    long fasterC(long n, int m) {
        List<Long> pisanoPeriod = getPisanoPeriod(m);
        int periodLength = pisanoPeriod.size() - 2;
        int remainderIndex = (int) (n % periodLength);
        return pisanoPeriod.get(remainderIndex);
    }

    private List<Long> getPisanoPeriod(int m) {
        List<Long> pisanoPeriod = new ArrayList<>();
        pisanoPeriod.add(0L);
        pisanoPeriod.add(1L);

        for (int i = 2; i <= m * 6; i++) {
            long fibModM = (pisanoPeriod.get(i - 1) + pisanoPeriod.get(i - 2)) % m;
            pisanoPeriod.add(fibModM);

            if (fibModM == 1 && pisanoPeriod.get(i - 1) == 0) {
                break;
            }
        }

        return pisanoPeriod;
    }
}