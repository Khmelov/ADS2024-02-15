package by.it.group351003.sologub.lesson04;

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

    void sort(int[] arr, int right, int left)
    {
        if (left < right) {
            int middle = left + (right - left) / 2;
            sort(arr, middle, left);
            sort(arr, right, middle + 1);
            mergeArr(arr, left, middle, right);
        }
    }

    void mergeArr(int arr[], int left, int middle, int right)
    {
        int size1 = middle - left + 1; // sizes
        int size2 = right - middle;
        int Left[] = new int[size1];
        int Right[] = new int[size2];

        // Copy data to temp arrays
        for (int i = 0; i < size1; ++i)
            Left[i] = arr[left + i];
        for (int j = 0; j < size2; ++j)
            Right[j] = arr[middle + 1 + j];
        int i = 0;
        int j = 0;
        int k = left;
        while (i < size1 && j < size2) {
            if (Left[i] <= Right[j]) {
                arr[k] = Left[i];
                i++;
            }
            else {
                arr[k] = Right[j];
                j++;
            }
            k++;
        }
        while (i < size1) {
            arr[k] = Left[i];
            i++;
            k++;
        }
        while (j < size2) {
            arr[k] = Right[j];
            j++;
            k++;
        }
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
        sort(a, n - 1, 0);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
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
