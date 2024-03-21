package by.it.group351001.semashka.lesson01;

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
        long pisanoPeriodLength = getPisanoPeriodLength(m);
        n = n % pisanoPeriodLength;
        long previous = 0;
        long current = 1;

        if (n == 0) return 0;
        else if (n == 1) return 1;

        for (int i = 2; i<=n; i++) {
            long temp = (previous + current) % m;
            previous = current;
            current = temp;
        }

        return current;
    }

    private long getPisanoPeriodLength(int m) {
        long a = 0, b = 1, c;
        for (int i = 0; i < m * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1) return i + 1;
        }
        return 0;
    }
}

