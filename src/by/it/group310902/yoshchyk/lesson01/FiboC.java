package by.it.group310902.yoshchyk.lesson01;

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
        long period = getPisanoPeriod(m); // менять тут
        long remainderIndex = n % period; // менять тут

        long current = 0;
        long next = 1;
        long newNum = 0;

        if (remainderIndex == 0) { // менять тут
            return 0;
        } else if (remainderIndex == 1) { // менять тут
            return 1;
        } else {
            for (int i = 1; i < remainderIndex; i++) { // менять тут
                newNum = (current + next) % m;
                current = next;
                next = newNum;
            }
        }

        return newNum;
    }

    long getPisanoPeriod(int m) { // менять тут
        long a = 0, b = 1, c = a + b;
        for (int i = 0; i < m * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1)
                return i + 1;
        }
        return 0;
    }


}

