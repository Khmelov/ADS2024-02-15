package by.it.group351002.yemelyanenka.lesson01;

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

            n = n % period;

            long prev = 0;
            long curr = 1;

            for (int i = 2; i <= n; i++) {
                long next = (prev + curr) % m;
                prev = curr;
                curr = next;
            }

            return curr;
        }

        int getPisanoPeriod(int m) {
            int prev = 0;
            int curr = 1;
            int per = 2;

            while (true) {
                int next = (prev + curr) % m;
                prev = curr;
                curr = next;

                if (prev == 0 && curr == 1) {
                    return per - 1;
                }

                per++;
            }
        }
    }




