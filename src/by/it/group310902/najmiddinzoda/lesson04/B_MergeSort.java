package by.it.group310902.najmiddinzoda.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    private int[] merge(int[] a, int[] b) {
        // Создаем массив result для хранения объединенного массива.
        int[] result = new int[a.length + b.length];
        // Индексы для массивов a (i) и b (j), а также для массива result (r).
        int i = 0, j = 0, r = 0;
        // Проходим по обоим массивам a и b и сравниваем элементы на каждой позиции.
        while (i < a.length && j < b.length) {
            // Если элемент в массиве a меньше или равен элементу в массиве b,
            // то добавляем элемент из a в result и увеличиваем i и r.
            if (a[i] <= b[j]) {
                result[r++] = a[i++];
            } else {
                // Иначе добавляем элемент из b в result и увеличиваем j и r.
                result[r++] = b[j++];
            }
        }
        // Добавляем оставшиеся элементы из массива a в result, если они есть.
        while (i < a.length) {
            result[r++] = a[i++];
        }
        // Добавляем оставшиеся элементы из массива b в result, если они есть.
        while (j < b.length) {
            result[r++] = b[j++];
        }
        // Возвращаем объединенный и отсортированный массив result.
        return result;
    }

    // Метод mergeSort выполняет сортировку слиянием для заданного массива a.
    private int[] mergeSort(int[] a) {
        // Если массив содержит 1 элемент или менее, то он уже отсортирован.
        if (a.length <= 1) {
            return a;
        }
        // Находим средний индекс массива.
        int mid = a.length / 2;
        // Создаем два подмассива: левый и правый.
        int[] left = new int[mid];
        int[] right = new int[a.length - mid];
        // Копируем элементы из массива a в подмассивы left и right.
        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, a.length - mid);
        // Рекурсивно вызываем mergeSort для подмассивов left и right.
        // Затем объединяем их с помощью метода merge.
        return merge(mergeSort(left), mergeSort(right));
    }

    // Метод getMergeSort считывает входные данные из потока stream и возвращает
// отсортированный массив, используя алгоритм сортировки слиянием.
    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // Считываем количество элементов в массиве.
        int n = scanner.nextInt();
        // Создаем массив a для хранения элементов.
        int[] a = new int[n];
        // Считываем элементы массива из входного потока.
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Выполняем сортировку слиянием для массива a и возвращаем результат.
        return mergeSort(a);
    }


}