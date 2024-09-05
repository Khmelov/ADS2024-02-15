package by.it.group310902.chyliuk.lesson04;

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

    // Метод для слияния и подсчета инверсий
    private long mergeAndCount(int[] arr, int l, int m, int r) {
        // Создаем временные подмассивы для левой и правой частей
        int[] left = new int[m - l + 1];
        int[] right = new int[r - m];

        // Копируем элементы из исходного массива во временные подмассивы
        System.arraycopy(arr, l, left, 0, m - l + 1);
        System.arraycopy(arr, m + 1, right, 0, r - m);

        // Инициализация индексов и счетчика инверсий
        int i = 0, j = 0, k = l;
        long swaps = 0;

        // Слияние временных подмассивов обратно в исходный массив
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j])
                arr[k++] = left[i++];
            else {
                arr[k++] = right[j++];
                // Если элемент из правой части меньше элемента из левой части,
                // это означает наличие инверсии, поэтому увеличиваем счетчик инверсий
                swaps += (m + 1) - (l + i);
            }
        }

        // Добавляем оставшиеся элементы из левого подмассива в исходный массив
        while (i < left.length)
            arr[k++] = left[i++];

        // Добавляем оставшиеся элементы из правого подмассива в исходный массив
        while (j < right.length)
            arr[k++] = right[j++];

        // Возвращаем общее количество инверсий
        return swaps;
    }

    // Метод для рекурсивного слияния и подсчета инверсий
    private long mergeSortAndCount(int[] arr, int l, int r) {
        long count = 0;

        // Если левая граница меньше правой, производим сортировку и подсчет инверсий
        if (l < r) {
            int m = (l + r) / 2;

            count += mergeSortAndCount(arr, l, m);
            count += mergeSortAndCount(arr, m + 1, r);
            count += mergeAndCount(arr, l, m, r);
        }

        // Возвращаем общее количество инверсий
        return count;
    }

    // Метод для подсчета инверсий в массиве
    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // Считываем размер массива
        int n = scanner.nextInt();
        int[] a = new int[n];

        // Считываем элементы массива
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Вызываем метод сортировки и подсчета инверсий и возвращаем результат
        return (int) mergeSortAndCount(a, 0, a.length - 1);
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
