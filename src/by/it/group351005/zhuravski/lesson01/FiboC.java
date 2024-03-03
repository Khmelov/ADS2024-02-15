package by.it.group351005.zhuravski.lesson01;

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
        int per = 0;
        int cur = 1;
        int prev = 0;
        int succ = 0;
        do {
            per++;
            succ = (cur + prev) % m;
            prev = cur;
            cur = succ;
        } while( !((cur == 1) && (prev == 0)));
        n = n % per;
        cur = 1;
        prev = 0;
        succ = 0;
        for (int i = 0; i < n; i++) {
            succ = (cur + prev) % m;
            prev = cur;
            cur = succ;
        }
        return prev;
    }


}

