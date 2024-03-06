package by.it.group351002.matsuev.lesson01;

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
        return findFiboNumber(n%findPisanoPeriod(m)) % m;
    }
    long findFiboNumber(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        long prev = 0;
        long cur = 1;
        long tmp;
        for (int i = 2; i <= n; i++) {
            tmp = cur;
            cur = prev + cur;
            prev = tmp;
        }
        return cur;
    }
    long findPisanoPeriod(int m) {
        long prev = 0;
        long cur = 1;
        long tmp;
        for (int i = 0; i < m * m; i++) {
            tmp = (cur + prev) % m;
            prev = cur;
            cur = tmp;
            if (prev == 0 & cur == 1) {
                return i + 1;
            }
        }
        return 0;
    }

}


