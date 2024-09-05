package by.it.group310901.yakovtsov.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
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
        for (int i = 0; i < n; i++) {//3 5 //2 4  //2 3 4 5
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием
        LinkedList<LinkedList<Integer>> parts = new LinkedList<LinkedList<Integer>>();
        LinkedList<Integer> curPart = new LinkedList<Integer>();
        curPart.add(a[0]);
        parts.add(curPart);
        for (int i = 1; i < n; i++) {
            curPart = parts.getLast();
            if (curPart.getLast() < a[i]) {
                curPart.add(a[i]);
            }
            else {
                curPart = new LinkedList<Integer>();
                curPart.add(a[i]);
                parts.add(curPart);
            }
        }
        LinkedList<LinkedList<Integer>> newParts;
        LinkedList<Integer> merge1;
        LinkedList<Integer> merge2;
        Integer it1;
        Integer it2;
        while (parts.size() > 1) {
            newParts = new LinkedList<LinkedList<Integer>>();
            if (parts.size() % 2 == 1) {
                newParts.add(parts.getLast());
                parts.removeLast();
            }
            while (!parts.isEmpty()) {
                merge1 = parts.getFirst();
                parts.removeFirst();
                merge2 = parts.getFirst();
                parts.removeFirst();
                curPart = new LinkedList<Integer>();
                while (!merge1.isEmpty() && !merge2.isEmpty()) {
                    it1 = merge1.getFirst();
                    it2 = merge2.getFirst();
                    if (it1 < it2) {
                        curPart.add(it1);
                        merge1.removeFirst();
                    }
                    else {
                        curPart.add(it2);
                        merge2.removeFirst();
                    }
                }
                if (!merge1.isEmpty()) {
                    curPart.addAll(merge1);
                }
                if (!merge2.isEmpty()) {
                    curPart.addAll(merge2);
                }
                newParts.add(curPart);
            }
            parts = newParts;
        }
        curPart = parts.getFirst();
        for (int i = 0; i < n; i++) {
            a[i] = curPart.getFirst();
            curPart.removeFirst();
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
