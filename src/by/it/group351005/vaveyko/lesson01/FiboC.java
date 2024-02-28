package by.it.group351005.vaveyko.lesson01;

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        ArrayList<Integer> mod = new ArrayList<>();
        mod.add(0);
        mod.add(1);
        mod.add(1);
        while ((mod.get(mod.size()-1) != 1 || mod.get(mod.size()-2) != 0)) {
            mod.add((mod.get(mod.size()-1) + mod.get(mod.size()-2)) % m);
        }
        return mod.get((int)n % (mod.size()-2));
    }


}

