package by.it.group351005.bitno.lesson01;

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
        if (n <= 2) return 1;

        int[] divisors = new int[100];
        int[] arrayOfFibNumbers = new int[100];
        int i = 1;

        arrayOfFibNumbers[0] = 1;
        arrayOfFibNumbers[1] = 1;
        divisors[0] = 1;
        divisors[1] = 1;
        do{
            i++;
            arrayOfFibNumbers[i] = arrayOfFibNumbers[i - 1] + arrayOfFibNumbers[i - 2];
            divisors[i] = arrayOfFibNumbers[i] % m;
        } while (divisors[i] != divisors[0]);
        int period = (int)(n % i);
        return divisors[period - 1];
    }


}

