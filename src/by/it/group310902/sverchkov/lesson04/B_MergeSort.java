package by.it.group310902.sverchkov.lesson04;

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

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием
        mergeSort(0, a.length - 1, a);


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }

    public void mergeSort(int l, int r, int[] arr) {
        if (l >= r) return;
        int mid = (l + r) / 2;
        mergeSort(l, mid, arr);
        mergeSort(mid + 1, r, arr);
        merge(l, r, arr);

    }

    public void merge(int l, int r, int[] arr) {
        int mid = (l + r) / 2;
        int[] left = new int[mid - l + 1];
        int[] right = new int[r - mid];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[l + i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i + 1];
        }

        int i = 0, j = 0, idx = 0;
        while (idx <= left.length + right.length) {
            if (i == left.length) {
                for (; j < right.length; j++) {
                    arr[l + idx] = right[j];
                    idx++;
                }
                break;
            }
            if (j == right.length) {
                for (; i < left.length; i++) {
                    arr[l + idx] = left[i];
                    idx++;
                }
                break;
            }
            if (left[i] < right[j]) {
                arr[l + idx] = left[i];
                i++;
            } else {
                arr[l + idx] = right[j];
                j++;
            }
            ++idx;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result = instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index : result) {
            System.out.print(index + " ");
        }
    }


}