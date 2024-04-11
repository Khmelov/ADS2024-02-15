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
        int temp = b;
        b = a;
        a = temp;
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

    int[] Merge (int[] arr, int left, int middle, int right){
        int EF = left;
        int ES = middle + 1;
        int CE = 0;

        while((EF <= middle) && (ES <= right))
            if (arr[EF] < arr [ES])
                arr[CE++] = arr[EF++];
            else
                arr[CE++] = arr[ES++];

        while (EF<= middle)
            arr[CE++] = arr[EF++];
        while (ES <= right)
            arr[CE++] = arr[ES++];

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
