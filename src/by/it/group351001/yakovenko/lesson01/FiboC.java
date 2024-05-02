package by.it.group351001.yakovenko.lesson01;

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
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        BigInteger M = BigInteger.valueOf(m);
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger t;

        long count = 2;
        for (int i = 1; i <= m * m -  1; i++) {
            t = a.add(b);

            if (t.mod(M).equals(BigInteger.ONE) && i!= 1 )
                if (b.mod(M).equals(BigInteger.ONE)  )
                    if (a.mod(M).equals(BigInteger.ZERO) )
                    {
                        count = i;
                        break;
                    }

            a = b;
            b = t;

        }
        count = n % (count) ;

        long otevet = 1;

        a = BigInteger.ZERO;
        b = BigInteger.ONE;
        t = BigInteger.ZERO;

        if (n <= 2)
        {
            otevet = 1;
        }
        else
        for (int i = 1; i <= count; i++) {
            otevet = t.mod(M).intValue();
            t = a.add(b);
            a = b;
            b = t;
        }

        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        return otevet;
    }
}