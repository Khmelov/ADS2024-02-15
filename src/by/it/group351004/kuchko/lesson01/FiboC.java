package by.it.group351004.kuchko.lesson01;

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
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        ArrayList<Long> Arr = new ArrayList<>();
        Arr.add(0L);
        Arr.add(1L);
        Arr.add(1L);
        while (Arr.get(Arr.size() - 2) != 0L || Arr.get(Arr.size() - 1) != 1L)
            Arr.add((Arr.get(Arr.size() - 2) + Arr.get(Arr.size() - 1)) % m);
        Arr.remove(Arr.size() - 1);
        Arr.remove(Arr.size() - 1);
        return Arr.get((int)(n % Arr.size()));

    }


}

