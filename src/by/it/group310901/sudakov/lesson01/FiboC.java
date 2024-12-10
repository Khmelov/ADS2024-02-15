package by.it.group310901.sudakov.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;
import java.util.List;

public class FiboC {

    private final long startTime = System.currentTimeMillis();

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
        long x = 0, y = 1;
        long i = 0;
        do {
            y = x + y;
            x = y - x;
            y = y % m;
            i++;
        } while (!(x == 0 && y == 1));
        long j = n % i;
        x = 0;
        y = 1;
        for (int k = 0; k < j; k++){
            y = x + y;
            x = y - x;
            y = y % m;
        }
        return x;
    }

}

