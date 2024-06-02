package by.it.group351001.ovchinnickov.lesson01;

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
        long[] mas;
        long result;
        mas = new long[100];
        mas[0]=0;
        mas[1]=1;
        int i=2;
        while(!(mas[i-2]==0 && mas[i-1]==1) || i<=2){
            mas[i]=(mas[i-2]+mas[i-1])%m;
            i+=1;
        }
        result = mas[(int) (n%(i-2))];
        return result;
    }


}

