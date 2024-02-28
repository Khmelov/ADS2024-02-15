package by.it.group351002.mikhalkov.lesson01;

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
        int period = getPisanoPeriod(m);

        long tmp = n % period;
        long prev = 0;
        long curr = 1;

        if (tmp == 0) {
            return prev;
        } else if (tmp == 1) {
            return curr;
        }

        for (int i = 2; i <= tmp; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr;
    }

    int getPisanoPeriod(int m) {

        int prev = 0;
        int curr = 1;
        int period = 0;

        for (int i = 0; i <= 6 * m; i++) {
            int temp = (prev + curr) % m;
            prev = curr;
            curr = temp;

            if (prev == 0 && curr == 1) {
                period = i + 1;
            }
        }
        return period;
    }
}

