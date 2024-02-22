package by.it.group351003.kolbeko.lesson01;

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
        int pisanoLen = 0;
        long fiboMod;
        long mod;
        long[] fiboNums = new long[6 * m];
        fiboNums[0] = 0;
        fiboNums[1] = 1;
        long[] fiboMods = new long[6 * m];
        fiboMods[0] = 0;
        fiboMods[1] = 1;
        // нахождение периода Пизано
        for (int i = 2; i <= (6 * m); i++) {
            fiboNums[i] = fiboNums[i - 1] + fiboNums[i - 2];
            fiboMods[i] = fiboNums[i] % m;
            pisanoLen++;
            if ((fiboMods[i - 1] == 0) && (fiboMods[i] == 1))
                break;
        }
        fiboMod = n % pisanoLen;
        mod = fiboMods[(int) fiboMod];
        return mod;
    }


}

