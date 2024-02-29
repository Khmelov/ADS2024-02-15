package by.it.group351001.rudenya_d.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n){
        if(n==0) {return BigInteger.ZERO;}
        if(n==1) {return BigInteger.ONE;}
        BigInteger[] mas = new BigInteger[n];
        mas[0] = BigInteger.ONE;
        mas[1] = BigInteger.ONE;
        for(int i=2; i<n; i++){
            mas[i] = mas[i-1].add(mas[i-2]);
        }
        return mas[n-1];
    }

}

