package by.it.group310901.kanunnikava;

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
        int period = getPisanoPeriod(m);
        int remainder = (int) (n % period);
        long a = 0;
        long b = 1;
        long result = remainder;
        for (int i = 1; i < remainder; i++)
        {
            result = (a+b)%m;
            a=b;
            b=result;
        }
        return result;
    }
    private int  getPisanoPeriod(int m)
    {
        int a=0;
        int b=1;
        int c=a+b;
        for (int i=0; i<m*m; i++)
        {
            c=(a+b)%m;
            a=b;
            b=c;
            if(a==0 && b==1)
                return i+1;
        }
        return 0;
    }

}

