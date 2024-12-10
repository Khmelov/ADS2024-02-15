package by.it.group310902.sverchkov.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;
import java.util.ArrayList;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 1;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано

        if (n == 1 || n == 0) return n;
        long cur = 1;
        long prev = 0;
        int k = 1;
        ArrayList<Long> fiboL = new ArrayList<>();
        fiboL.addFirst(Long.valueOf("0"));
        fiboL.add(1,Long.valueOf("1"));
        for (int i = 1; i < n*n; i++) {
            long temp = cur;
            cur += prev;
            prev = temp;
            fiboL.add(i,temp);
            if (cur % m == 1 && prev % m == 0) break;
            k++;
        }
        int t = (int) n%k;
        return fiboL.get(t)%m;
    }


}

