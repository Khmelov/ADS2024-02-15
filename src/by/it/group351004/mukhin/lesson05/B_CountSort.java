package by.it.group310901.stashuk.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

<<<<<<< HEAD

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_CountSort.class.getResourceAsStream("dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

=======
>>>>>>> 6f7b5783 (Lesson 4, 5)
    int[] countSort(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Читаем размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        // Читаем числа
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }
<<<<<<< HEAD
        //тут реализуйте логику задачи с применением сортировки подсчетом
        int max = points[0], min = max;
        for (int i = 1; i < points.length; i++) {
            min = Math.min(points[i], min);
            max = Math.max(points[i], max);
        }

        int[] newArr = new int[max - min + 1];
        for (int point : points) newArr[point - min]++;

        int pos = 0;
        for (int i = 0; i < newArr.length; i++) {
            while (newArr[i]-- > 0)
                points[pos++] = min + i;
            //newArr[i]--;
=======

        // Находим максимальное и минимальное значения
        int min = points[0];
        int max = points[0];
        for (int i = 1; i < n; i++) {
            if (points[i] < min) {
                min = points[i];
            }
            if (points[i] > max) {
                max = points[i];
            }
        }

        // Создаем массив счетчиков
        int[] count = new int[max - min + 1];

        // Считаем количество вхождений каждого числа
        for (int i = 0; i < n; i++) {
            count[points[i] - min]++;
        }

        // Восстанавливаем упорядоченную последовательность
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                points[index] = i + min;
                index++;
                count[i]--;
            }
>>>>>>> 6f7b5783 (Lesson 4, 5)
        }

        return points;
    }

<<<<<<< HEAD
}
=======
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
>>>>>>> 6f7b5783 (Lesson 4, 5)
