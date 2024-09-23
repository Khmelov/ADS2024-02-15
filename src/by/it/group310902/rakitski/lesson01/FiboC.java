package by.it.group310902.rakitski.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 55555;
        int m = 1000;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    long fasterC(long n, int m) {
        //Интуитивно найти решение не всегда просто и
        //возможно потребуется дополнительный поиск информации
        int a[]=new int[m*m+2];
        a[0]=0;
        a[1]=1;
        int k=0;
        for(int i=2;i<m*m+1;++i){
            a[i]=(a[i-1]+a[i-2])% m;
            if(a[i]==a[1] && a[i-1]==0){
                k=i;
                break;
            }
        }
        k-=1;
        return a[((int)n%k)];
    }


}

