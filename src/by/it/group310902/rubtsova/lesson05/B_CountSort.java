package by.it.group310902.rubtsova.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {
    int[] countSort(InputStream stream) throws FileNotFoundException {
        // Создаем сканнер для чтения данных из потока ввода
        Scanner scanner = new Scanner(stream);

        // Читаем количество элементов в массиве (n)
        int n = scanner.nextInt();
        int[] points = new int[n]; // Создаем массив для хранения элементов

        // Читаем элементы массива и сохраняем их в points
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        // Создаем массив для подсчета количества элементов каждого значения (от 0 до 10)
        int[] count = new int[11];

        // Подсчитываем количество вхождений каждого элемента в массиве points
        for (int i = 0; i < n; i++) {
            count[points[i]]++;
        }

        // Создаем массив для отсортированных элементов
        int[] sortedPoints = new int[n];
        int currentSortedIndex = 0;

        // Проходим по массиву count и добавляем каждый элемент в отсортированный массив sortedPoints
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                sortedPoints[currentSortedIndex++] = i;
                count[i]--;
            }
        }

        // Возвращаем отсортированный массив
        return sortedPoints;
    }



    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_CountSort.class.getResourceAsStream("dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
