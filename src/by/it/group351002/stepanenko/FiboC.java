package by.it.group351002.stepanenko;

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
        long period = getPeriodLength(m);
        long remainder = n % period;
        long previous = 0;
        long current = 1;

        if (remainder == 0)
            return 0;
        else if (remainder == 1)
            return 1;

        for (int i = 2; i <= remainder; i++) {
            long temp = (current + previous) % m;
            previous = current;
            current = temp;
        }

        return current;
    }

    private long getPeriodLength(int m) {
        long previous = 0;
        long current = 1;
        long period = 0;

        for (int i = 0; i < m * m; i++) {
            long temp = (current + previous) % m;
            previous = current;
            current = temp;

            if (previous == 0 && current == 1) {
                period = i + 1;
                break;
            }
        }

        return period;
    }
}

