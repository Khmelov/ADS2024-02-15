package by.it.group310902.strizhevskiy.lesson01;

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

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        long cache0 = 0;
        long cache1 = 1;

        for (int i = 0; i < n/2; i++) {
            cache0 += cache1;
            if(cache0 >= m) cache0 -= m;
            cache1 += cache0;
            if(cache1 >= m) cache1 -= m;
        }

        return n % 2 == 0 ? cache0 : cache1;
    }


}
