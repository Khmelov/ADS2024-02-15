package by.it.group351002.stepanenko.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

public class FiboB {
    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d\n", n, fibo.fastB(n));
    }

    BigInteger fastB(int n) {
        ArrayList<BigInteger> nums = new ArrayList<>(n + 1);
        nums.add(BigInteger.ZERO);
        nums.add(BigInteger.ONE);

        for (int i = 2; i <= n; i++) {
            BigInteger f = nums.get(i - 2).add(nums.get(i - 1));
            nums.add(f);
        }

        return nums.get(n);
    }
}