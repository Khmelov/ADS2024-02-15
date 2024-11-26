package by.it.group310901.sorochuk.lesson01;

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
    //Этот метод вычисляет число Фибоначчи с использованием быстрого алгоритма,
// который имеет временную сложность O(n) и использует O(n) памяти.
    BigInteger fastB(Integer n) {
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        BigInteger[] arr = new BigInteger[n+1];
//Создается массив array размером n+1, где каждый элемент будет хранить значение числа Фибоначчи.
// Начальные значения массива устанавливаются как 0 и 1.
//  Затем в цикле от 2 до n каждый элемент массива вычисляется
// как сумма двух предыдущих элементов (array[i-1] и array[i-2]), используя метод add класса BigInteger.
        if (n==0) return BigInteger.ZERO;
        if (n==1) return BigInteger.ONE;

        arr[0] = BigInteger.valueOf(0);
        arr[1] = BigInteger.valueOf(1);

        for (int i = 2; i <= n; i++) {
            arr[i]=arr[i -1].add(arr[i -2 ]);
        }
        return arr[n];
    }

}

