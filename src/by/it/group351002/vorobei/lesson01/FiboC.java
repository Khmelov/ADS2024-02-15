package by.it.group351002.vorobei.lesson01;

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
        long[] Array= new long[6*m];
        int i = 2;
        boolean flag = true;

        Array[0] = 0L;
        Array[1] = 1L;

        if (n==1) return 1L;
        else {
            while (flag) {
                Array[i] = Array[i - 1] + Array[i - 2];
                if ((Array[i] % m == 1) && (Array[i - 1] % m == 0)) flag = false;
                else i++;

            }

            return (Array[(int) n % (i - 1)] % m);
        }
    }


}

