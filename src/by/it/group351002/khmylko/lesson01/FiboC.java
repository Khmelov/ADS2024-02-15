package by.it.group351002.khmylko.lesson01;
import java.util.Scanner;
import java.util.Vector;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

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

    static   long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        /*
         * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
         * необходимо найти остаток от деления n-го числа Фибоначчи на m.
         * время расчета должно быть не более 2 секунд
         */

        Long[] Mas = new Long[3];
        Long[] Mas1 = new Long[6*m+2];
        Mas[0] = 1l;
        Mas[1] = 1l;
        Mas1[0]=1l;
        Mas1[1]=1l;

        int j = 2 ;
        do {
            Mas[2] = Mas[1]+Mas[0];
            Mas[0] = Mas[1];
            Mas[1] = Mas[2];

            Mas1[j]= Mas[2] % m;

            j++;
        } while(!((Mas1[j-1]==1)&&(Mas1[j-2]==1)));
        j=j-2;
        long Result;
        int Pos=(int) (n%j);

        Result = Mas1[Pos-1];

        return Result;
    }


}