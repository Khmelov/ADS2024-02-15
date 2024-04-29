package by.it.group310902.pashkovich.lesson04;

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

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_MergeSort.class.getResourceAsStream("dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result = instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        mergeSort(a);


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }

    void mergeSort(int[] arr) {
        if (arr == null) {
            return;
        }
        int n = arr.length;
        if (n <= 1) {
            return;
        }

        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        // Заполнение левого массива
        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        // Заполнение правого массива
        for (int i = mid; i < n; i++) {
            right[i - mid] = arr[i];
        }

        mergeSort(left); // Рекурсивный вызов для левого массива
        mergeSort(right); // Рекурсивный вызов для правого массива
        merge(arr, left, right); // Слияние левого и правого массивов
    }

    void merge(int[] arr, int[] left, int[] right) {
        int nL = left.length;
        int nR = right.length;
        int i = 0, j = 0, k = 0;

        // Слияние left[] и right[] в arr[]
        while (i < nL && j < nR) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        // Дописываем оставшиеся элементы из left[]
        while (i < nL) {
            arr[k++] = left[i++];
        }

        // Дописываем оставшиеся элементы из right[]
        while (j < nR) {
            arr[k++] = right[j++];
        }
    }

}
