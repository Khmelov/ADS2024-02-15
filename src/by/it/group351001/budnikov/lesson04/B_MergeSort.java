package by.it.group351001.budnikov.lesson04;

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

    int[] Merge(int[] a) {
        if (a.length == 1) {
            return a;
        }

        int[] b = new int[a.length / 2];
        int[] c = new int[a.length - b.length];
        int[] res = new int[a.length];

        System.arraycopy(a, 0, b, 0, b.length);
        System.arraycopy(a, b.length, c, 0, c.length);

        b = Merge(b);
        c = Merge(c);

        int b_k=0, c_k=0, res_k=0;
        while(b_k < b.length || c_k < c.length) {
            if (c_k >= c.length || (b_k < b.length && b[b_k] < c[c_k])) {
                res[res_k] = b[b_k];
                res_k++;
                b_k++;
            }
            else {
                res[res_k] = c[c_k];
                res_k++;
                c_k++;
            }
        }
        return res;

    }
    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] a=new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        return Merge(a);
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group351001/budnikov/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
