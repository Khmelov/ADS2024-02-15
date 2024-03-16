package by.it.group351002.golovko.lesson01;

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

    public static long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пи
        int [] mas;
        mas=new int [6*m];
        if (m==1) return 0;
        mas[0]=0;
        mas[1]=1;
        int i=1;
        do {
            i++;
            mas[i]=(mas[i-1]+mas[i-2])%m;
        } while(!(mas[i]==1 && mas[i-1]==0));
        i=(int)n%(i-1);
        return mas[i];

    }


}

