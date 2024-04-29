package by.it.group310901.baradzin.lesson01;

import java.util.ArrayList;

/**
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5, необходимо найти остаток от деления n-го числа Фибоначчи на m. время
 * расчета должно быть не более 2 секунд
 */

public class FiboC {
    private final long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        var fibo = new FiboC();
        var n = 10;
        var m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Решение сложно найти интуитивно. Возможно потребуется дополнительный поиск информации. См. период Пизано
     */
    long fasterC(int n, int m) {
        var pisano = new ArrayList<Long>();
        pisano.add(0L);
        for (long fib1 = 1L, fib2 = 1L; fib1 % m != 0 || fib2 % m != 1; fib2 = fib1 + (fib1 = fib2))
            pisano.add(fib1 % m);
        return pisano.get(n % pisano.size());
    }
}
