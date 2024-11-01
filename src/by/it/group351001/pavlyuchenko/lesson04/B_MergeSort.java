package by.it.group351001.pavlyuchenko.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

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

    void Merge(int[] arr, int low1, int high1, int high2) {
      int[] temp1 = new int[high1-low1+1];
      int[] temp2 = new int[high2-high1];
      temp1 = Arrays.copyOfRange(arr,low1, high1+1);
      temp2 = Arrays.copyOfRange(arr,high1+1, high2+1);
      int i = 0;
      int j = 0;
      int k = 0;
      while ((i < high1-low1+1) && (j < high2-high1)) {
        if (temp1[i] < temp2[j]) {
          arr[k] = temp1[i];
          i++;
        } else {
            arr[k] = temp2[j];
            j++;
        }
        k++;
      }
      while (i < high1-low1+1) {
        arr[k] = temp1[i];
        i++;
        k++;
      }
      while (j < high2-high1) {
          arr[k] = temp2[j];
          j++;
          k++;
      }
    }

    void MergeSort(int[] arr, int low1, int high) {
      if (low1 < high) {
          int mid = (low1 + high) >> 1;
        MergeSort(arr,low1, mid);
        MergeSort(arr,mid+1, high);
          if (arr[mid] > arr[mid+1]) {
              Merge(arr,low1,mid, high);
          }
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
        int low = 0;
        int high = a.length-1;
        MergeSort(a, low, high);


        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием






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
