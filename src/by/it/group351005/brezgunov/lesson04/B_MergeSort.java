package by.it.group351005.brezgunov.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Array;
import java.util.Arrays;
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

    int[] mergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        int[] left = mergeSort(Arrays.copyOf(array, array.length / 2));
        int[] right = mergeSort(Arrays.copyOfRange(array, array.length / 2, array.length));
        int[] merged = new int[left.length + right.length];

        int leftIndex = 0, rightIndex = 0;
        for (int i = 0; i < merged.length; i++) {
            if (leftIndex < left.length) {
                if (rightIndex < right.length) {
                    merged[i] = left[leftIndex] > right[rightIndex] ? right[rightIndex++] : left[leftIndex++];
                } else {
                    merged[i] = left[leftIndex++];
                }
            } else if (rightIndex < right.length) {
                merged[i] = right[rightIndex++];
            }
        }
        return merged;
    }

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием
        a = mergeSort(a);





        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by\\it\\group351005\\brezgunov\\lesson04\\dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
