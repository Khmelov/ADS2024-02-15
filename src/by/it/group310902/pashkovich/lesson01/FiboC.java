package by.it.group310902.pashkovich.lesson01;

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
        long previous = 0, current = 1;
        long period = 0;
        for (long i = 0; i < m * m; i++) {
            long temp = current;
            current = (previous + current) % m;
            previous = temp;
            if (previous == 0 && current == 1) {
                period = i + 1;
                break;
            }
        }

        n = n % period;
        previous = 0;
        current = 1;
        if (n <= 1) {
            return n;
        }
        for (long i = 0; i < n - 1; i++) {
            long temp = current;
            current = (previous + current) % m;
            previous = temp;
        }
        return current;
    }


}

