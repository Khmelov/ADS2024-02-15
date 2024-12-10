package by.it.group351004.ulasko.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;
import java.util.List;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    long fasterC(long n, int m) {
        List<Integer> list = new ArrayList<>();
        int temp = 0;
        int n0 = 0;
        int n1 = 1;
        boolean isNotFound = true;
        list.add(n0);
        list.add(n1);
        while (isNotFound){
            temp = (n0 + n1) % m;
            n0 = n1;
            n1 = temp;
            list.add(temp);
            if (list.get(list.size() - 1) == 1 && list.get(list.size() - 2) == 0)
                isNotFound = false;
        }
        return list.get((int)(n % (list.size() - 2)));
    }


}

