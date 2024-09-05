package by.it.group351002.golovko.lesson04;

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

    public static void mergeSort(int[] arr,int n){
        if (n==1) {return;}
        int mid=n/2;
        int[] l=new int[mid];
        int[] r=new int[n-mid];
        for (int i=0;i<mid;i++){
            l[i]=arr[i];
        }
        for (int i=mid;i<n-mid;i++){
            r[i]=arr[i];
        }
        mergeSort(l,mid);
        mergeSort(r,n-mid);
        merge(arr,l,r,mid,n-mid);
    }
    public static void merge(int[]arr,int[]l,int[]r,int left,int right){
        int i=0; int j=0;int k=0;
        while ((i<left) && (j<right)){
            if (l[i]<=r[j]) {
                arr[k++]=l[i++];
            }
            else arr[k++]=r[j++];
        }
        while (i<left) {
            arr[k++]=l[i++];
        }
        while (j<right) {
            arr[k++]=r[j++];
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
        mergeSort(a,n);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }


}
