package by.it.group351001.rudenya_d.lesson01;

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
        int n = 999999999;
        int m = 321;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        long o;
        ArrayList<Long> mas = new ArrayList<>();
        long i = 1L;
        mas.add(0L);
        mas.add(1L);
        while((mas.get((int)i)+mas.get((int)i-1))%m!=1 || mas.get((int)i)%m!=0){
           mas.add(mas.get((int)i)+mas.get((int)i-1));
           i++;
        }
        o = n%(i+1);
        o = mas.get((int)o);
        o = o%m;
        return o;
    }
}

