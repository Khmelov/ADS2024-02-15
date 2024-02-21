package EgorB.lesson01;
import java.util.ArrayList;
import java.util.List;
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
        if (n <= 1) {
            return n;
        }

        List<Long> fibModulo = new ArrayList<>();
        fibModulo.add(0L);
        fibModulo.add(1L);

        int period = 0;
        for (int i = 2; i <= n; i++) {
            long fibMod = (fibModulo.get(i - 1) + fibModulo.get(i - 2)) % m;
            fibModulo.add(fibMod);

            // Период Пизано начинается с 0, 1
            if (fibModulo.get(i) == 1 && fibModulo.get(i - 1) == 0) {
                period = i - 1;
                break;
            }
        }

        // Вычисление остатка от деления n на period
        int remainder = (int) (n % period);

        return fibModulo.get(remainder);
    }


}

