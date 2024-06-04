package by.it.group351001.semashka_iv.lesson04;

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
    void mergeSort(int[] a, int l, int r){
        if (l<r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);


            int i1= l, i2 = m+1;
            int n = 0;
            int[] merged = new int [r-l+1];
            while ((i1<=m)&&(i2<=r)){
                if (a[i1]<a[i2]){
                    merged[n++] = a[i1++];
                }else{
                    merged[n++] = a[i2++];
                }
            }
            while (i1<=m){
                merged[n++] = a[i1++];
            }
            while (i2<=r){
                merged[n++] = a[i2++];
            }

            if (r + 1 - l >= 0) System.arraycopy(merged, 0, a, l, r + 1 - l);
        }
    }

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
/*
Чтение входных данных: Программа считывает размер массива n, а затем сам массив a из n элементов.

Сортировка слиянием: Реализация сортировки слиянием в методе mergeSort.
Основные шаги алгоритма:

Разбиваем массив пополам до тех пор, пока размер разделенной части не станет равным 1.
Объединяем две отсортированные части массива в одну.
Сливаем два упорядоченных подмассива в один новый подмассив, сохраняя упорядоченность элементов.
Записываем отсортированный подмассив обратно в исходный массив.
Повторяем процесс до тех пор, пока все подмассивы не будут объединены в один отсортированный массив.
Вывод результата: Отсортированный массив выводится на экран в консоль
*/
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

        mergeSort(a,0,a.length-1);






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