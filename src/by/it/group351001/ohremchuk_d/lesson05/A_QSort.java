package by.it.group351001.ohremchuk_d.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
 * Видеорегистраторы и площадь.
 * На площади установлена одна или несколько камер.
 * Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы).
 * Известен список событий на площади (время начала каждого события).
 * Вам необходимо определить для каждого события сколько камер его записали.
 *
 * В первой строке задано два целых числа:
 * число включений камер (отрезки) 1<=n<=50000
 * число событий (точки) 1<=m<=50000.
 *
 * Следующие n строк содержат по два целых числа ai и bi (ai<=bi) -
 * координаты концов отрезков (время работы одной какой-то камеры).
 * Последняя строка содержит m целых чисел - координаты точек.
 * Все координаты не превышают 10E8 по модулю (!).
 *
 * Точка считается принадлежащей отрезку, если она находится внутри него или на границе.
 *
 * Для каждой точки в порядке их появления во вводе выведите,
 * скольким отрезкам она принадлежит.
 *
 * Sample Input:
 * 2 3
 * 0 5
 * 7 10
 * 1 6 11
 *
 * Sample Output:
 * 1 0 0
 */

public class A_QSort {

    // Класс, представляющий отрезок (включение и выключение камеры)
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        // Конструктор для инициализации отрезка
        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
            // Проверка, если концы отрезка заданы в обратном порядке
            if (this.start > this.stop) {
                int temp = this.start;
                this.start = this.stop;
                this.stop = temp;
            }
        }

        // Метод для сравнения отрезков по началу (start)
        @Override
        public int compareTo(Segment o) {
            return Integer.compare(this.start, o.start);
        }
    }

    // Метод быстрой сортировки для массива отрезков
    Segment[] QSort(Segment[] segments, int left, int right) {
        Segment pivot = segments[(left + right) / 2]; // Опорный элемент
        int i = left, j = right;
        while (i <= j) {
            while (segments[i].start < pivot.start) ++i;
            while (segments[j].start > pivot.start) --j;
            if (i <= j) {
                Segment temp = segments[i];
                segments[i] = segments[j];
                segments[j] = temp;
                ++i;
                --j;
            }
        }
        if (left < j) QSort(segments, left, j);
        if (i < right) QSort(segments, i, right);
        return segments;
    }

    // Метод для определения количества отрезков, которым принадлежит каждая точка
    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Чтение числа отрезков и точек
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // Чтение отрезков
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // Чтение точек
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортировка отрезков по началу
        QSort(segments, 0, n - 1);

        // Подсчет количества отрезков для каждой точки
        for (int i = 0; i < m; i++) {
            int point = points[i];
            for (int j = 0; j < n; j++) {
                if (segments[j].start > point) {
                    // Если начало текущего отрезка больше точки, дальнейшие отрезки не нужны
                    break;
                }
                if (segments[j].stop >= point) {
                    // Если точка принадлежит текущему отрезку
                    result[i]++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
