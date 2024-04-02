package by.it.group351005.egorov.lesson04;

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

    void merger(int[] a,int l,int m,int r){
        // make a copies of arrays
        int length1 = m - l;
        int length2 = r - m + 1;
        int[] leftArray = new int[length1];
        for (int i = 0; i < length1; i++) {
            leftArray[i] = a[l+i];
        }
        int[] rightArray = new int[length2];
        for (int i = 0; i < length2; i++) {
            rightArray[i] = a[m+i];
        }
        //merging
        int i = 0;
        int j = 0;
        int k = l;
        while(k <= r){
            if (i == length1){
                a[k++] = rightArray[j++];
            }
            else if (j == length2){
                a[k++] = leftArray[i++];
            }
            else if (leftArray[i] < rightArray[j]){
                a[k++] = leftArray[i++];
            }
            else{
                a[k++] = rightArray[j++];
            }
        }
    }

    void mergeSorter (int[] a, int l,int r){
        if (r <= l)
            return;
        int m = (r + l) /2;
        mergeSorter(a,l,m);
        mergeSorter(a,m+1,r);
        merger(a,l,m+1,r);

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
        mergeSorter(a,0,n - 1);





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
