package by.it.group351005.bychkowski;
import java.util.Vector;

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
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        Vector<Long> vector = new Vector<>();
        long newFib0 = 1L;
        long newFib1 = 1L;
        long newFib2 = 1l;
        int i=1;
        vector.add(newFib0);
        vector.add(newFib1);


        do {
            newFib2=(newFib0+newFib1)%m;
            newFib0=newFib1;

            newFib1=newFib2;

            vector.add(newFib2);

            i++;

        }while (vector.get(i)!=1 || vector.get(i-1)!=1);
        i++;
        i-=2;
        while(n-i>0){
            n=n-i;
        }
        n--;

        return vector.get((int)n);
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано

    }


}

