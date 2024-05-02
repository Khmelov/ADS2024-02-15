package by.it.group351003.gornik_artur.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private int[] a; // Массив для хранения элементов
    private int[] tempArray; // Вспомогательный массив для сортировки
    private int count; // Переменная для подсчета инверсий

    // Метод для подсчета инверсий в массиве
    public int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream); // Создание объекта Scanner для чтения данных из потока ввода
        int n = scanner.nextInt(); // Чтение размера массива
        a = new int[n]; // Инициализация массива
        tempArray = new int[n]; // Инициализация вспомогательного массива
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt(); // Чтение элементов массива
        }
        mergeSort(0, n - 1); // Вызов метода сортировки слиянием
        return count; // Возвращаем количество инверсий
    }

    // Рекурсивный метод для сортировки слиянием
    private void mergeSort(int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            Thread leftThread = new Thread(() -> { // Создаем поток для сортировки левой половины
                mergeSort(left, middle);
            });
            Thread rightThread = new Thread(() -> { // Создаем поток для сортировки правой половины
                mergeSort(middle + 1, right);
            });
            leftThread.start();
            rightThread.start();
            try {
                leftThread.join(); // Ожидаем завершения потока для сортировки левой половины
                rightThread.join(); // Ожидаем завершения потока для сортировки правой половины
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            merge(left, middle, right);
        }
    }

    // Метод для слияния отсортированных частей массива
    private void merge(int left, int middle, int right) {
        for (int i = left; i <= right; i++) {
            tempArray[i] = a[i];
        }
        int i = left;
        int j = middle + 1;
        int k = left;
        while (i <= middle && j <= right) {
            if (tempArray[i] <= tempArray[j]) {
                a[k++] = tempArray[i++];
            } else {
                a[k++] = tempArray[j++];
                count += (middle - i + 1);
            }
        }
        while (i <= middle) {
            a[k++] = tempArray[i++];
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        long finishTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("Execution time: " + (finishTime - startTime) + " ms");
    }
}
