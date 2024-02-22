package non_valid_packages.rakizkiy.lesson01;

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
        long g = Predic(m);
        n = n % g;
        long a = 0;
        long b = 1;
        long k = 1;
        long temp = n;

        for (int i = 1; i < n; i++) {
            temp = (a + b) % m;
            a = b;
            b = temp;
        }

        return temp;
    }


    long Predic(int m){
        long a = 0;
        long b = 1;
        for (long i = 0; i < m*m; ++i) {
            long temp = (a + b) % m;
            a = b;
            b = temp;
            if (b == 1 && a == 0) {
                return i+1;
            }
        }
        return 0;
    }




}