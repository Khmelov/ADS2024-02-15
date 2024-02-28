package by.it.group351003.kisel.lesson01;

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
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fasterC(n, m), fibo.time());
    }

    public static long pisano(long m) {
        long prev = 0;
        long curr = 1;
        long period = 0;
        long temp;
        for (int i = 0; i < m * 6; i++) {
            temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
            if (prev == 0 && curr == 1)
                period = i + 1;
        }
        return period;
    }

    public static long fasterC(long n, long m) {
        long pisanoPeriod = pisano(m);
        n = n % pisanoPeriod;
        long prev = 0;
        long curr = 1;
        long temp;
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        for (int i = 0; i < n - 1; i++) {
            temp = (curr + prev) % m;
            prev = curr;
            curr = temp;
        }
        return curr % m;
    }
}

