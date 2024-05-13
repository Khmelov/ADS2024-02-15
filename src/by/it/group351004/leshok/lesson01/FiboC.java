package by.it.group351004.leshok.lesson01;

import java.util.Vector;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.Vector;

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
        //см. период Пизане

        long fibPr = 0;
        long fib = 1;
        Vector<Long> v = new Vector();

        v.add(fibPr);
        v.add(fib);
        for (int i = 1; i < n; i++)
        {
            long fibOld = fib;
            fib = (fib + fibPr) % m;;
            fibPr = fibOld;

            if (fibPr == 0 && fib == 1) {
                v.remove(v.size() - 1);
                break;
            }
            else
                v.add(fib);
        }
        int offset = (int)(n % v.size());
        return v.get(offset);
    }


}
