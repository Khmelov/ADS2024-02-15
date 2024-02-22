package by.it.a_khmelev.lesson01;

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
        //здесь нужно реализовать вариант с временем O(n) и памятью O(n)
        int[]  arr = new int[3];
        arr[0] = 1;
        arr[1] = 1;
        for(int i = 2; i < n; i++){
            arr[2] = arr[0] + arr[1];
            arr[1] = arr[2] - 1;
            arr[0] = arr[2] - 2;

        }
        return new BigInteger(String.valueOf(arr[2]);
    }

}

