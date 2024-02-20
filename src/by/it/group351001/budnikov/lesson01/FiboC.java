package by.it.group351001.budnikov.lesson01;

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
        // Хоть по условию и стоит ограничение 10^18, однако в тестах значения long.
        long n = 999999999;
        int m = 321;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //см. период Пизано
        return (getFibo(n % getPisano(m)) % m);
        // return 0L;
    }

    long getFibo(long n) {

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        long prev = 0;
        long curr = 1;
        long temp;

        for (int i = 2; i <= n; i++) {
            temp = curr;
            curr = prev + curr;
            prev = temp;
        }

        return curr;
    }

    long getPisano(int m) {
        int prev = 0;
        int curr = 1;
        int temp;

        for (int i = 0; i < 6 * m; i++) {
            temp = curr;
            curr = (curr + prev) % m;
            prev = temp;

            if (curr == 1 && prev == 0) {
                return i + 1;
            }
        }
        return -1;
    }

}