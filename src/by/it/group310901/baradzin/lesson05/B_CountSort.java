package by.it.group310901.baradzin.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10. Выведите упорядоченную
 * по неубыванию последовательность этих чисел.</p>
 * <p>При сортировке реализуйте метод со сложностью O(n).</p>
 * <p><a href="https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/">Пример</a></p>
 * <p><a href="http://programador.ru/sorting-positive-int-linear-time/">Вольный перевод</a></p>
 */

public class B_CountSort {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataB.txt");
        var instance = new B_CountSort();
        var result = instance.countSort(stream);
        for (var index : result)
            System.out.print(index + " ");
    }

    void sort(int[] array, int min, int max) {
        var count = new int[max - min + 1];
        for (var i = 0; i < array.length; i++)
            count[array[i] - min]++;
        for (int j, i = j = 0; i < count.length; i++)
            for (var k = 0; k < count[i]; k++)
                array[j++] = i + min;
    }

    void sort(int[] array) {
        int min, max = min = array[0];
        for (var i = 1; i < array.length; i++) {
            if (array[i] < min) min = array[i];
            else if (array[i] > max) max = array[i];
        }
        sort(array, min, max);
    }

    int[] countSort(InputStream stream) {
        var scanner = new Scanner(stream);
        var points = new int[scanner.nextInt()];
        for (var i = 0; i < points.length; i++)
            points[i] = scanner.nextInt();
        sort(points);
        return points;
    }
}
