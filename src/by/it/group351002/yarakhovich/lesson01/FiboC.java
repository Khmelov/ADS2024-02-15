package by.it.group351002.yarakhovich.lesson01;

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
        if (n == 1) return 1;
        int[] FiboArr = new int[m*6];

        int i;
        FiboArr[1] = 1;
        for(i = 2; i < m*6; i++)
        {
            FiboArr[i] = (FiboArr[i-1] + FiboArr[i-2]) % m;
            if ((FiboArr[i] == 1) && (FiboArr[i-1] == 0)) break;
        }
        i -= 1;

        i = (int) n % i;

        return FiboArr[i];
    }


}

