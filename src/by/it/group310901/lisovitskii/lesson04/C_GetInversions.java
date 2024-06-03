package by.it.group310901.lisovitskii.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
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

    long calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        return getInversionsOptimal(a);
    }
        //!!!!!!!!!!!!!!!!!!!!!!!!тут ваше решение!!!!!!!!!!!!!!!!!!!!!!!!

        public static long getInversionsNaive(int[] arr) {
            long c = 0;
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] > arr[j]) {
                        c += 1;
                    }
                }
            }
            return c;
        }

        public static long getInversionsOptimal(int[] arr) {
            return mergeSortAndCount(arr, 0, arr.length - 1);
        }

        static int mergeAndCount(int[] arr, int l, int m, int r) {
            int[] left = Arrays.copyOfRange(arr, l, m + 1);
            int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
            int i = 0, j = 0, k = l, swaps = 0;
            while (i < left.length && j < right.length) {
                if (left[i] <= right[j])
                    arr[k++] = left[i++];
                else {
                    arr[k++] = right[j++];
                    swaps += (m + 1) - (l + i);
                }
            }
            while (i < left.length)
                arr[k++] = left[i++];
            while (j < right.length)
                arr[k++] = right[j++];
            return swaps;
        }

        public static long mergeSortAndCount(int[] arr, int l, int r) {
            long count = 0;

            if (l < r) {
                int m = (l + r) / 2;
                count += mergeSortAndCount(arr, l, m);
                count += mergeSortAndCount(arr, m + 1, r);
                count += mergeAndCount(arr, l, m, r);
            }
            return count;
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        long result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
