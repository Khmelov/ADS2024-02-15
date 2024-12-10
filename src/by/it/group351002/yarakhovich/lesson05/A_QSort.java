package by.it.group351002.yarakhovich.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь.
На площади установлена одна или несколько камер.
Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы)
Известен список событий на площади (время начала каждого события).
Вам необходимо определить для каждого события сколько камер его записали.

В первой строке задано два целых числа:
    число включений камер (отрезки) 1<=n<=50000
    число событий (точки) 1<=m<=50000.

Следующие n строк содержат по два целых числа ai и bi (ai<=bi) -
координаты концов отрезков (время работы одной какой-то камеры).
Последняя строка содержит m целых чисел - координаты точек.
Все координаты не превышают 10E8 по модулю (!).

Точка считается принадлежащей отрезку, если она находится внутри него или на границе.

Для каждой точки в порядке их появления во вводе выведите,
скольким отрезкам она принадлежит.
    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/

public class A_QSort {

    // Отрезок
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
            // Тут лучше дополнить конструктор на случай, если
            // концы отрезков придут в обратном порядке
        }

        @Override
        public int compareTo(Segment o) {
            // Компаратор отрезков должен возвращать следующие значения:
            // -1, если текущий отрезок (this) меньше переданного отрезка (o)
            // 0, если текущий отрезок (this) равен переданному отрезку (o)
            // 1, если текущий отрезок (this) больше переданного отрезка (o)
            if (this.stop < o.stop) {
                return -1;
            } else if (this.stop > o.stop) {
                return 1;
            } else {
                if (this.start < o.start) {
                    return -1;
                } else if (this.start > o.start) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    // Метод быстрой сортировки
    private void quickSort(Segment[] segments, int low, int high) {
        if (low < high) {
            int pi = partition(segments, low, high);
            quickSort(segments, low, pi - 1);
            quickSort(segments, pi + 1, high);
        }
    }

    // Вспомогательный метод для разделения массива
    private int partition(Segment[] segments, int low, int high) {
        Segment pivot = segments[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (segments[j].compareTo(pivot) <= 0) {
                i++;
                swap(segments, i, j);
            }
        }
        swap(segments, i + 1, high);
        return i + 1;
    }

    // Вспомогательный метод для обмена элементов массива
    private void swap(Segment[] segments, int i, int j) {
        Segment temp = segments[i];
        segments[i] = segments[j];
        segments[j] = temp;
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];

        // Число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // Читаем сами отрезки
        for (int i = 0; i < n; i++) {
            // Читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // Читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки
        quickSort(segments, 0, n - 1);

        // Выполняем проверку принадлежности точек отрезкам
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;
            for (Segment segment : segments) {
                if (segment.start > point) {
                    break;
                }
                if (segment.start <= point && point <= segment.stop) {
                    count++;
                }
            }
            result[i] = count;
        }

        return result;
    }

    public static void main(String[]args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}