package by.it.group310901.baradzin.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Реализуйте сортировку слиянием для одномерного массива. Сложность алгоритма должна быть не хуже, чем O(n log n)
 * </p>
 * <ol>
 * <li>Первая строка содержит число 1<=n<=10000,</li>
 * <li>вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.</li>
 * </ol>
 * <p>Необходимо отсортировать полученный массив.</p>
 * <p>
 * Sample Input:<br/>
 * 5<br/>
 * 2 3 9 2 9<br/>
 * Sample Output:<br/>
 * 2 2 3 9 9<br/>
 * </p>
 */

public class B_MergeSort {

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataB.txt");
        var instance = new B_MergeSort();
//        var startTime = System.currentTimeMillis();
        var result = instance.getMergeSort(stream);
//        var finishTime = System.currentTimeMillis();
        for (var index : result)
            System.out.print(index + " ");
    }

    int[] getMergeSort(InputStream stream) {
        var scanner = new Scanner(stream);
        var a = new int[scanner.nextInt()];
        for (var i = 0; i < a.length; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }
        mergeSort(a, 0, a.length);
        return a;
    }

    void mergeSort(int[] arr, int left, int right) {
        if (right - left < 2) return;
        mergeSort(arr, left, (left + right) >>> 1, right);
    }

    void mergeSort(int[] arr, int left, int mid, int right) {
        mergeSort(arr, left, mid);
        mergeSort(arr, mid, right);
        var range = Arrays.copyOfRange(arr, left, mid);
        var i = 0;
        while (i < range.length && mid < right)
            if (range[i] <= arr[mid])
                arr[left++] = range[i++];
            else
                arr[left++] = arr[mid++];
        while (i < range.length)
            arr[left++] = range[i++];
        while (mid < right)
            arr[left++] = arr[mid++];
    }
}
