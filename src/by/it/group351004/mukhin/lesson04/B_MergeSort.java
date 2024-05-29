package by.it.group351004.mukhin.lesson04;

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

    void swapElements(int a, int b){
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    int[] MergeSort(int[] arr, int  left, int right){
        if (right - left >= 2){
            int middle = (left + right) / 2;

            MergeSort(arr, left, middle);
            MergeSort(arr, middle + 1, right);
            Merge(arr, left, middle, right);
        } else if ((right - left == 1) && (arr[right] > arr[left]))
            swapElements(arr[right],arr[left]);

        return arr;
    }

    int[] Merge(int[] arr, int left, int middle, int right) {
        int leftIndex = left;
        int rightIndex = middle + 1;
        int mergedIndex = 0;
        int[] mergedArray = new int[right - left + 1];

        while (leftIndex <= middle && rightIndex <= right) {
            if (arr[leftIndex] < arr[rightIndex])
                mergedArray[mergedIndex++] = arr[leftIndex++];
            else
                mergedArray[mergedIndex++] = arr[rightIndex++];
        }

        while (leftIndex <= middle)
            mergedArray[mergedIndex++] = arr[leftIndex++];
        while (rightIndex <= right)
            mergedArray[mergedIndex++] = arr[rightIndex++];

        System.arraycopy(mergedArray, 0, arr, left, mergedArray.length);

        return arr;
    }

    int[] getMergeSort(InputStream stream) {

        Scanner scanner = new Scanner(stream);

        int size = scanner.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
            System.out.println(arr[i]);
        }

        MergeSort (arr, 0, size - 1);

        return arr;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
