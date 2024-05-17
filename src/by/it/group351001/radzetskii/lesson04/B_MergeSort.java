package by.it.group351001.radzetskii.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
       int[] sortedArr=mergeSort(a);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return sortedArr;
    }
    private int[] merge(int[]a, int[]b){
        int i=0;
        int j=0;
        int [] result=new int[a.length+b.length];
        for (int k=0; k<result.length;k++){
            if (j==b.length || (i<a.length && a[i]<=b[j])){
                result[k]=a[i];
                i++;
            }
            else
            {
                result[k]=b[j];
                j++;
            }
        }
        return result;
    }
    private int[] mergeSort(int [] startArr){
        if (startArr.length==1) return startArr;
        int n=startArr.length;
        int m=n/2;
        int[] left=new int[m];
        int[] right= new int[n-m];
        System.arraycopy(startArr,0,left,0,m);
        System.arraycopy(startArr,m,right,0,n-m);
        left=mergeSort(left);
        right=mergeSort(right);
        return merge(left,right);
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
