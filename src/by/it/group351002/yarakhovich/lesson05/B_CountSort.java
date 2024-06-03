package by.it.group351002.yarakhovich.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
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
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        // Читаем точки
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        // Определяем максимальное и минимальное значения в массиве
        int min = Arrays.stream(points).min().orElse(0);
        int max = Arrays.stream(points).max().orElse(0);

        // Создаем массив для подсчета количества точек
        int[] count = new int[max - min + 1];

        // Подсчитываем количество каждой точки
        for (int point : points) {
            count[point - min]++;
        }

        // Восстанавливаем отсортированный массив
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            int frequency = count[i];
            while (frequency > 0) {
                points[index] = i + min;
                index++;
                frequency--;
            }
        }

        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}