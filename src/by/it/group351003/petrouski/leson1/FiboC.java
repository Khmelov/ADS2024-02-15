package by.it.group351003.petrouski.leson1;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 20;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизана
        ArrayList<Long> fibo = new ArrayList<>();
        fibo.add(0L);
        fibo.add(1L);
        fibo.add(1L);
        while (fibo.get(fibo.size() - 2) != 0L || fibo.get(fibo.size() - 1) != 1L)
            fibo.add((fibo.get(fibo.size() - 2) + fibo.get(fibo.size() - 1)) % m);
        fibo.remove(fibo.size() - 1);
        fibo.remove(fibo.size() - 1);
        return fibo.get((int)(n % fibo.size()));
    }

}

