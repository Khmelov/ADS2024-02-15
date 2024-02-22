package group351005.brezgunov.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;
import java.util.Arrays;

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
        long[] nums = new long[4 * m];
        nums[0] = 1;
        nums[1] = 1;
        int i = 2;
        do {
            nums[i] = nums[i - 1] + nums[i - 2];
            i++;
        } while ((nums[i - 1]) % m != 1 || (nums[i - 2]) % m != 1);
        return (nums[(int)((n - 1) % (i - 2))] % m);
    }
        }