package by.it.group351003.kalinckovich.lesson01;

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
        // Вычисляем период Пизано
        int pisanoPeriod = getPisanoPeriod(m);
        // Вычисляем n по модулю длины периода
        n = n % pisanoPeriod;

        Long prev = Long.valueOf(0);
        Long curr = Long.valueOf(1);

        for (int i = 0; i < n - 1; i++) {
            Long temp = curr;
            curr = curr + (prev);
            prev = temp;
        }

        return curr%(Long.valueOf(m));
    }
    public static int getPisanoPeriod(int m) {
        int prev = 0;
        int curr = 1;
        int res = 0;

        for (int i = 0; i < m * m; i++) {
            int temp = 0;
            temp = curr;
            curr = (prev + curr) % m;
            prev = temp;

            if (prev == 0 && curr == 1) {
                res = i + 1;
                break;
            }
        }

        return res;
    }
}



