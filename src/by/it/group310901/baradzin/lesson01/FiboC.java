package by.it.group310901.baradzin.lesson01;

import java.util.ArrayList;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {
    private final long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    // Решение сложно найти интуитивно
    // возможно потребуется дополнительный поиск информации
    // см. период Пизано
    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано

        if (n == 1) return 1;

        int[] allNums = new int[m*6];
        allNums[0]=0;
        allNums[1]=1;
        int i = 2;
        for(; i<allNums.length; i++){
            allNums[i] = (allNums[i-1]+allNums[i-2])%m;
            if ((allNums[i]==1) && (allNums[i-1]==0)) break;
        }
        i -= 1;

        i = (int) n%i;

        return allNums[i];
    }
}
