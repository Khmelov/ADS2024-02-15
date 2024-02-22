package by.it.group351005.melnikov.lesson01;

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

    BigInteger fastB(Integer n) {
        //люблюгит
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
      if (n==1||n==2) return BigInteger.ONE;
      BigInteger[] Arr=new BigInteger[3];
      Arr[0]=BigInteger.ONE;
        Arr[1]=BigInteger.ONE;
        for(int i =2 ; i<n;i++){
            Arr[2]=Arr[0].add(Arr[1]);
            Arr[0]=Arr[1];
            Arr[1]=Arr[2];
        }
        return Arr[2];
    }

}

