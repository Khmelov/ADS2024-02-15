package by.it.group351001.strizhak.lesson04;

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
        // Вычисление числа инверсий
        int result = mergeSortAndCountInversions(a, 0, n - 1);
        return result;
    }

    private int mergeSortAndCountInversions(int[] array, int left, int right) {
        int countInversions = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Рекурсивно сортируем две половины массива и вычисляем число инверсий
            countInversions += mergeSortAndCountInversions(array, left, mid);
            countInversions += mergeSortAndCountInversions(array, mid + 1, right);

            // Слияние отсортированных половин и вычисление числа инверсий
            countInversions += mergeAndCountInversions(array, left, mid, right);
        }
        return countInversions;
    }

    private int mergeAndCountInversions(int[] array, int left, int mid, int right) {
        // Размеры временных массивов
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Создание временных массивов
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Копирование элементов во временные массивы
        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[mid + 1 + j];
        }

        // Слияние временных массивов обратно в основной массив и вычисление числа инверсий
        int i = 0;  // Индекс для временного массива L
        int j = 0;  // Индекс для временного массива R
        int k = left;  // Индекс для основного массива
        int countInversions = 0;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
                countInversions += n1 - i; // Все элементы, начиная с L[i], образуют инверсии с R[j]
            }
            k++;
        }

        // Копирование оставшихся элементов из временного массива L
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        // Копирование оставшихся элементов из временного массива R
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }

        return countInversions;
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
