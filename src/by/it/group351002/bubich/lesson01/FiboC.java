package by.it.group351002.bubich.lesson01;
import java.util.Vector;
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

        Vector<Long> vector = new Vector<>();
        long newFib0 = 0L;
        long newFib1 = 1L;
        long newFib2;
        vector.add(newFib0);
        vector.add(newFib1);
        newFib2 = newFib0 + newFib1;
        newFib0 = newFib1;
        newFib1 = newFib2;
        vector.add(newFib2 % m);
        while (vector.get(0) != vector.get(vector.size()-2) || vector.get(1) != vector.get(vector.size()-1)){
            newFib2 = newFib0 + newFib1;
            newFib0 = newFib1;
            newFib1 = newFib2;
            vector.add(newFib2 % m);
        }
        vector.remove(vector.size() - 1);
        vector.remove(vector.size() - 1);
        int temp = (int)(n % vector.size());
        return vector.get(temp);
    }


}