package by.it.group351002.alexeichik.Lesson01;

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
        Boolean Flag;
        Integer[] arr=new Integer[ 6*m];
        int numost = 0;
        if (n==1) return 1;
        else {
            arr[0] = 0;
            arr[1] = 1;
            int i = 2;
            int pizano = 2;
            Flag = true;
            while ((i < 6*m) && Flag) {
                arr[i] = (arr[i - 1] + arr[i - 2]) % m;
                if ((arr[i] == 1) && (arr[i - 1] == 0)) Flag = false;
                else
                    pizano++;
                i++;
            }
            numost = (int) (n % pizano);
        }
        return arr[numost];
    }





}

