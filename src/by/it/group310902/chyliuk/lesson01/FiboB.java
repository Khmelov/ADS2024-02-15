package by.it.group310902.chyliuk.lesson01;

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
        BigInteger MyArray[] = new BigInteger[n+1];
        MyArray[0] = BigInteger.ZERO;
        MyArray[1] = BigInteger.ONE;
        BigInteger Array[] = new BigInteger[n+1];
            for(int i = 2; i <= n; i++){
                MyArray[i] =  MyArray[i-1].add(MyArray[i-2]);
            }

        return MyArray[n];
    }

}

