package by.it.group310902.karpechenko.lesson04;

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
    void merge(int[] x, int l, int r){
        int m = (l + r)/2;
        int lsize = m - l + 1;
        int rsize = r - m;

        int[] left = new int[lsize];
        int[] right = new int[rsize];

        for (int i = 0; i < lsize; i++)
            left[i] = x[i+l];
        for (int i = 0; i < rsize; i++)
            right[i] = x[i+m+1];

        int i = 0, j = 0, idx = l;
        while (i < lsize && j < rsize){
            if(left[i] <= right[j]){
                x[idx] = left[i];
                i++;
            }
            else{
                x[idx] = right[j];
                j++;
            }
            idx++;
        }
        while (i < lsize){
            x[idx] = left[i];
            i++;
            idx++;
        }
        while (j < rsize){
            x[idx] = right[j];
            j++;
            idx++;
        }
    }
    void merge_sort(int[] x, int l, int r){
        if (l >= r) return;
        int m = l + (r - l) / 2;
        merge_sort(x,l,m);
        merge_sort(x,m+1,r);
        merge(x,l,r);
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
            System.out.print(a[i]+" ");
        }
        System.out.println();

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием
        merge_sort(a,0,n-1);// :)
        // !!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310902/karpechenko/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
