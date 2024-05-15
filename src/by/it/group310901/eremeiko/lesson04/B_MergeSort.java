package by.it.group310901.eremeiko.lesson04;

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

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        // Создаем и заполняем массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием

        // Вызываем функцию сортировки слиянием
        a = sort(a);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // Возвращаем отсортированный массив
        return a;
    }

    public int[] sort(int[] arr) {
        // Если массив пустой или состоит из одного элемента, возвращаем его же
        if (arr == null)
            return null;
        if (arr.length < 2)
            return arr;

        // Делим массив на две половины
        int[] arrA = new int[arr.length / 2];
        System.arraycopy(arr, 0, arrA, 0, arrA.length);

        int[] arrB = new int[arr.length - arrA.length];
        System.arraycopy(arr, arrA.length, arrB, 0, arr.length - arrA.length);

        // Рекурсивно сортируем каждую половину
        arrA = sort(arrA);
        arrB = sort(arrB);

        // Сливаем отсортированные половины в один массив
        return merge(arrA, arrB);
    }

    public int[] merge(int[] arrA, int[] arrB) {
        // Инициализируем позиции в массивах A и B
        int positionA = 0, positionB = 0;

        // Создаем массив для хранения результата
        int[] arr = new int[arrA.length + arrB.length];

        // Пока в одном из массивов есть элементы...
        for (int i = 0; i < arr.length; i++) {
            // Если в массиве A больше нет элементов, берем элемент из B
            if (positionA == arrA.length) {
                arr[i] = arrB[positionB];
                positionB++;
            }
            // Если в массиве B больше нет элементов, берем элемент из A
            else if (positionB == arrB.length) {
                arr[i] = arrA[positionA];
                positionA++;
            }
            // Если следующий элемент в A меньше следующего элемента в B, берем элемент из A
            else if (arrA[positionA] < arrB[positionB]) {
                arr[i] = arrA[positionA];
                positionA++;

            }
            // В противном случае берем элемент из B
            else {
                arr[i] = arrB[positionB];
                positionB++;
            }
        }
        
        // Возвращаем отсортированный массив
        return arr;
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
