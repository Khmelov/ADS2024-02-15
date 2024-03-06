package by.it.group310901.dritz.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {

    private final long startTime = System.currentTimeMillis();

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
        long[] pisanoPeriod = new long[m * 6];    pisanoPeriod[0] = 0L;
        pisanoPeriod[1] = 1L;
        int pisanoPeriodSize = 0;
        for (int i = 2; i < m * 6; i++) {        pisanoPeriod[i] = (pisanoPeriod[i - 1] + pisanoPeriod[i - 2]) % m;
            pisanoPeriodSize++;
            if (pisanoPeriod[i] == 1 && pisanoPeriod[i - 1] == 0) {            break;
            }    }
        int index = (int) (n % pisanoPeriodSize);
        return pisanoPeriod[index];
         }
    }



