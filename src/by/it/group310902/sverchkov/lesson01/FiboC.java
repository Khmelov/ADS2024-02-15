package by.it.group310902.sverchkov.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 6;
        int m = 4;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано

        long cur = 1;
        long prev = 0;
//        int i = 2;
//        while (i < n) {
//            long temp = cur;
//            cur += prev;
//            prev = temp;
//            if (cur%m == 1 && prev%m == 0) break;
//            i++;
//        }
        int i = 0;
        for (i=0; i < n; i++) {
            long temp = cur;
            cur += prev;
            prev = temp;
            if (cur % m == 1 && prev % m == 0) break;
        }
        return n % i;
    }


}

