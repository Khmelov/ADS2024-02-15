package by.it.group351004.mukhin.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;
import java.util.List;

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
        List<Long> Fib = new ArrayList<>();
        Fib.add(0L);
        Fib.add(1L);
        int i = 2;
        while (!(Fib.get(i - 2) == 0 && Fib.get(i - 1) == 1) || i <= 2) {

            Fib.add((Fib.get(i - 2) + Fib.get(i - 1)) % m);

            i++;
        }
        return Fib.get((int) (n % (i - 2)));
    }


}

