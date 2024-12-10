package by.it.group351005.gaiduk.lesson01;

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
        if(n < 3) return 1;
        int[] divisor = new int[100];
        int[] massiv = new int[100];
        int i = 1;
        massiv[0] = 1;
        massiv[1] = 1;
        divisor[1] = 1;
        divisor[0] = 1;
        do{
            i++;
            massiv[i] = massiv[i - 1] + massiv[i - 2];
            divisor[i] = massiv[i] % m;
        }while(divisor[i] != divisor[0]);
        int periodC = (int)(n % i);
        return divisor[periodC - 1];
    }
}

