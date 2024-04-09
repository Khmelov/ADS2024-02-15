package by.it.group351002.yanachkin.lesson04;

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
    private static int inverseCount;
    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        sort(a);








        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return inverseCount;
    }

    public static int[] sort(int[] array) {
        inverseCount = 0;
        if (array == null) return null;
        if (array.length == 0) return array;
        return sort(array,0,array.length - 1);
    }

    static int[] sort(int[] array, int low, int high) {
        if (low > high - 1) return new int[] { array[low] };
        int mid = low + (high - low) / 2;

        return merge(sort(array,low,mid), sort(array,mid + 1, high));
    }

    static int[] merge(int[] array1, int[] array2) {
        int cursor1 = 0;
        int cursor2 = 0;
        int[] merged = new int[array1.length + array2.length];
        int mergedCursor = 0;

        while (cursor1 < array1.length && cursor2 < array2.length) {

            if (array1[cursor1] <= array2[cursor2])
                merged[mergedCursor++] = array1[cursor1++];
            else {
                merged[mergedCursor++] = array2[cursor2++];
                inverseCount += (array1.length - cursor1);
            }
        }

        while (cursor1 < array1.length)
            merged[mergedCursor++] = array1[cursor1++];

        while (cursor2 < array2.length)
            merged[mergedCursor++] = array2[cursor2++];

        return merged;
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