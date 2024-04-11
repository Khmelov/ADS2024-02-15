package by.it.group351005.vaveyko.lesson04;

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

    int[] copy(int[] fromArr, int startPt, int size) {
        int[] answer;
        if (fromArr.length >= startPt + size)
            answer = new int[size];
        else
            answer = new int[fromArr.length-startPt];

        for (int i = 0; i < answer.length; i++)
            answer[i] = fromArr[i+startPt];
        return answer;
    }

    int[] merge(int[] firstArr, int[] secondArr) {
        int firstPt = 0, secondPt = 0;
        int i = 0;
        int[] merged = new int[firstArr.length + secondArr.length];
        while (firstPt < firstArr.length && secondPt < secondArr.length)
            merged[i++] = firstArr[firstPt] < secondArr[secondPt] ? firstArr[firstPt++] : secondArr[secondPt++];
        while (firstPt < firstArr.length)
            merged[i++] = firstArr[firstPt++];
        while (secondPt < secondArr.length)
            merged[i++] = secondArr[secondPt++];
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
        int size = 1;
        while (size <= a.length) {
            int[] leftArr = new int[size];
            int[] rightArr = new int[size];
            int leftPt = 0;
            int rightPt = leftPt + size;
            while (rightPt <= a.length) {
                leftArr = copy(a, leftPt, size);
                rightArr = copy(a, rightPt, size);
                int[] merged = merge(leftArr, rightArr);
                for (int i = leftPt; i < leftPt + merged.length; i++) {
                    a[i] = merged[i-leftPt];
                }
                leftPt += 2 * size;
                rightPt += 2 * size;
            }

            size *= 2;
        }





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
