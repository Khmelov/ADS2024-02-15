package by.it.group351001.pavlyuchenko.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    private int Merge(int[] arr, int low1, int high1, int high2, int inversions) {
        int[] temp1 = new int[high1-low1+1];
        int[] temp2 = new int[high2-high1];
        temp1 = Arrays.copyOfRange(arr,low1, high1+1);
        temp2 = Arrays.copyOfRange(arr,high1+1, high2+1);
        int i = 0;
        int j = 0;
        int k = 0;
        while ((i < high1-low1+1) && (j < high2-high1)) {
            if (temp1[i] <= temp2[j]) {
                arr[k] = temp1[i];
                i++;
            } else {
                arr[k] = temp2[j];
                inversions += (high1-i+1);
                j++;
            }
            k++;
        }
        while (i < high1-low1+1) {
            arr[k] = temp1[i];
            i++;
            k++;
        }
        while (j < high2-high1) {
            arr[k] = temp2[j];
            j++;
            k++;
        }
        return inversions;
    }

    private int MergeSort(int[] arr, int low1, int high, int inversions) {
        if (low1 < high) {
            int mid = (low1 + high) >> 1;
            MergeSort(arr,low1, mid, inversions);
            MergeSort(arr,mid+1, high, inversions);
            if (arr[mid] > arr[mid+1]) {
                inversions = Merge(arr,low1,mid, high, inversions);
            }
        }
        return inversions;
    }


    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int low = 0;
        int high = a.length-1;
        int inversions = 0;
       inversions = MergeSort(a, low, high, inversions);

        int result = inversions;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!









        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
