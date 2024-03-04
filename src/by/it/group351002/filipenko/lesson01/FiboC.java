package by.it.group351002.filipenko.lesson01;

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
        int[] arr = new int[m * m + 2];
        int x = 0;
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i <= m * m + 1; ++i) {
            arr[i] = (arr[i - 2] + arr[i - 1]) % m;
            if (arr[i] == 1 && arr[i - 1] == 0) {
                x = i;
                break;
            }
        }
        x = x - 1;
        return arr[((int) n % x)];
    }
}

