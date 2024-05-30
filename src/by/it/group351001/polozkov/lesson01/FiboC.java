package by.it.group351001.polozkov.lesson01;

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        BigInteger M = BigInteger.valueOf(m);
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger sum = BigInteger.ZERO;

        long period = 2;
        for (int i = 1; i <= m * m - 1; i++) {

            if ((i != 1) && (b.mod(M).equals(BigInteger.ONE)) && (a.mod(M).equals(BigInteger.ZERO))) {
                period = i;
                break;
            }
            sum = a.add(b);
            a = b;
            b = sum;
        }
        period = n % period;



        a = BigInteger.ZERO;
        b = BigInteger.ONE;
        long resultat = 1;
        if (n > 2)
            for (int i = 1; i <= period - 1; i++) {

                sum = a.add(b);
                a = b;
                b = sum;
                resultat = sum.mod(M).intValue();
            }
        return resultat;
    }
}

