package by.it.group351004.mukhin.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

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
        ArrayList<BigInteger> numbers = new ArrayList<>(n);
        numbers.add(BigInteger.ZERO);
        numbers.add(BigInteger.ONE);
        int i = 2;
        while (i <= n){
            BigInteger f = numbers.get(i - 2).add(numbers.get(i - 1));
            numbers.add(f);
            i++;
        }
        return numbers.get(n);

    }

}

