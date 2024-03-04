package by.it.group351001.pavlyuchenko.lesson01;
import java.util.ArrayList;
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
        int n = 99999999;
        int m = 321;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        int fib = 0;
        int curr = 1;
        int temp,currMod,period;
        long result;
        ArrayList<Integer> mods = new ArrayList<>();
        mods.add(0);
        mods.add(curr%m);
        fib = fib + curr;
        currMod = fib%m;
        while ((currMod != 0) && (((curr+fib) % m ) != 1))  {
          mods.add(currMod);
          temp = fib;
          fib = fib + curr;
          curr = temp;
          currMod = fib%m;
        }
        period = mods.size();
        currMod = (int) (n % period);
        result = mods.get(currMod);
        return result;
    }


}

