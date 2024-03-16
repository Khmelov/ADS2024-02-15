package by.it.group351004.gasyuk.lesson01;

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
        long a = 0;
        long b = 1;
        long period = 0;
        int i;
        long answer = 0;
        for (i = 0; i < 6 * m * m; i++) {
            period = (a + b) % m;
            a = b;
            b = period;
            if (a == 0 & b == 1) {
                period = i + 1;
                i = 6 * m * m;
            }
        }

        n = n % (period);

        a = 0;
        b = 1;
        if (n == 0)
            answer = 0;
        else if (n == 1)
            answer = 1;
        else
        for (i = 2; i <= n; i++) {
            answer = (a + b) % m;
            a = b;
            b = answer;
        }

        return answer;
    }



}

