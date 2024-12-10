package by.it.group310902.krukovich.lesson01;

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
        int x = 0, y = 1, z = x + y;
        for (int i = 0; i < m * m; i++) {
            z = (x + y) % m;
            x = y;
            y = z;
            if (x == 0 && y == 1) {
                return i + 1;
            }
        }
        return -1;
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        if (n < 2) return n;
        long residue = n % pisanoPeriod(m);
        long x = 0, y = 1, z = residue;
        for (int i = 2; i <= residue; i++) {
            z = (x + y) % m;
            x = y;
            y = z;
        }

        return z % m;
    }




    }




