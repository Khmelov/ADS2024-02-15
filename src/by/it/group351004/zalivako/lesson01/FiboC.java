package by.it.group351004.zalivako.lesson01;

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
        if (n <= 1){
            return n % m;
        }
        long prev;
        long curr = 1;
        long ln;
        long l1 = 0;
        long l2 = 1;
        for (int i = 2; i <= n ; i++) {
            ln = l1 + l2;
            prev = curr;
            curr = ln % m;
            l1 = l2;
            l2 = ln;
            if (prev == 0 && curr == 1){
                long period = i - 1;
                long remainder = n % period;
                long pn;
                long p1 = 0;
                long p2 = 1;
                if (remainder <= 1){
                    return remainder % m;
                }
                for (int j = 2; j < period - 1; j++) {
                    pn = p1 + p2;
                    p1 = p2;
                    p2 = pn;
                    if (j == remainder)
                        return pn % m;
                }
            }
        }
        return curr;
    }
}