package by.it.group310902.rubtsova.lesson01;

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано

        int[] a = new int[m * m + 2];
        a[0] = 0;
        a[1] = 1;
        int k = 0;
        for (int i = 2; i<=m*m+1; ++i){
            a[i]=(a[i-2] + a[i-1]) % m;
            if (a[i] == a[1] && a[i-1] == a[0]) {
                k = i;
                break;
            }
        }
        k-=1;//уменьшается на 1, тк к было индексом последнего элемента а не длинной периода
        return a[((int) n % k)];//остаток от деления н на длинну периода к, получение соответствующего элемента
    }

}

