package by.it.group351001.budnikov.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {
    public static int result;
    int[] Merge(int[] a) {
        if (a.length == 1) {
            return a;
        }

        int[] b = new int[a.length / 2];
        int[] c = new int[a.length - b.length];
        int[] res = new int[a.length];

        System.arraycopy(a, 0, b, 0, b.length);
        System.arraycopy(a, b.length, c, 0, c.length);

        b = Merge(b);
        c = Merge(c);

        int b_k=0, c_k=0, res_k=0;
        while(b_k < b.length || c_k < c.length) {
            if (c_k >= c.length || (b_k < b.length && b[b_k] <= c[c_k])) {
                res[res_k] = b[b_k];
                res_k++;
                b_k++;
            }
            else {
                // Here we need to result += b.length-b_k
                if (b_k < b.length && c_k < c.length && b[b_k] > c[c_k]) result += b.length-b_k;
                res[res_k] = c[c_k];
                res_k++;
                c_k++;
            }
        }
        return res;
    }
    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        int n = scanner.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        result = 0;
        Merge(a);
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group351001/budnikov/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}