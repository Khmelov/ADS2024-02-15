package by.it.group310902.perova.lesson04;

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
 void merge(int[] a, int b1, int c1, int b2, int c2) {
     int i1 = b1, i2 = b2;
     int[] merged = new int[c2 - b1 +1];
     int k = 0;

     while (i1 <= c1 && i2 <= c2) {
         if (a[i1] <= a[i2])
             merged[k++] = a[i1++];
         else
             merged[k++] = a[i2++];
     }

     while (i1 <= c1)
         merged[k++] = a[i1++];

     while (i2 <= c2)
         merged[k++] = a[i2++];

     for (int i = b1, j = 0; i <= c2; i++, j++)
         a[i] = merged[j];

 }

 void mergeSort(int[] a, int b, int c) {

     if (b < c) {
         int m = (b +c) / 2;

         mergeSort(a, b, m);
         mergeSort(a, m + 1, c);
         merge(a, b, m, m +1, c);
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

        mergeSort(a, 0, n - 1);

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
