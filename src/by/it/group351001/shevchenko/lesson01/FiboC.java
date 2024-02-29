package by.it.group351001.shevchenko.lesson01;

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        List<Integer> pizano = new ArrayList<>();
        pizano.add(0);
        pizano.add(1);
        int pizanoPeriod = 1;
        do {
            pizanoPeriod++;
            pizano.add((pizano.get(pizanoPeriod - 1) + pizano.get(pizanoPeriod - 2)) % m);

        } while (!((pizano.get(pizanoPeriod) == 0) && (pizano.get(pizanoPeriod-1) == 1)));

        int mod = (int) (n % pizanoPeriod);
        return pizano.get(mod);
    }


}

