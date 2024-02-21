package by.it.group351005.gorodko.lesson01;

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
        long[] fiboArray = new long[3];
        long[] remainderArray = new long[6*m+2];
        fiboArray[0] = 0;
        fiboArray[1] = 1;
        remainderArray[0] = 0;
        remainderArray[1] = 1 % m;
        if (n == 1) return 1;
        else {
            int i = 2;
            do {
                fiboArray[2] = fiboArray[1] + fiboArray[0];
                remainderArray[i] = fiboArray[2] % m;
                fiboArray[0] = fiboArray[1];
                fiboArray[1] = fiboArray[2];
                i++;
            } while (!((remainderArray[i - 1] == 1) && (remainderArray[i - 2] == 0)));
            return (remainderArray[(int)n % (i - 2)]);
        }
    }

}

