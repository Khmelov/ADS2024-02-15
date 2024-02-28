package by.it.group310901.navrosuk.lesson01;

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

    public static int pisanoPeriod(int m) {
        int first = 0, second = 1, ans = 0;
        for (int i = 0; i < m * m; i++) {
            int temp;
            temp = (first + second) % m;
            first = second;
            second = temp;
            if (first == 0 && second == 1) // Начало нового периода Пизано
                 ans = i + 1;
        }
        return ans;
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        n = n % pisanoPeriod(m);

        int pred = 0;
        int nast = 1;
        if(n==1) return 1;
        if(n==0) return 0;

        for(int i=2; i<=n; ++i)
        {
            int temp;
            temp = nast;
            nast = (pred + nast) % m;
            pred = temp;
        }
        return nast;
    }


}

