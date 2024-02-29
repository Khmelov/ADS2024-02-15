package by.it.group310901.kovalyova.lesson01;

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
        long n = 35;
        int m = 5;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        if (n <= 1){
            return n%m;
        }
        long previous = 0;
        long current = 1;
        long new_current = 0;
        long period = 0;
        for(int i = 2; i <= n; i++){
            new_current = previous + current;
            previous = current;
            current = new_current;
            period++;
            if ((previous%m == 0)&&(current%m == 1)){
                n = (int)n%period;
                return fasterC(n,m);
            }
        }
        return new_current%m;
    }


}

