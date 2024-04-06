package by.it.group310901.baradzin.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Видеорегистраторы и площадь.</p>
 * <p>На площади установлена одна или несколько камер.</p>
 * <p>Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы). Известен список событий
 * на площади (время начала каждого события). Вам необходимо определить для каждого события сколько камер его
 * записали.</p>
 * <p>В первой строке задано два целых числа:</p>
 * <ol>
 * <li>число включений камер (отрезки) 1<=n<=50000;</li>
 * <li>число событий (точки) 1<=m<=50000.</li>
 * </ol>
 * <p>Следующие n строк содержат по два целых числа ai и bi (ai<=bi) - координаты концов отрезков (время работы одной
 * какой-то камеры). Последняя строка содержит m целых чисел - координаты точек. Все координаты не превышают 10E8 по
 * модулю (!).</p>
 * <p>Точка считается принадлежащей отрезку, если она находится внутри него или на границе.</p>
 * <p>Для каждой точки в порядке их появления во вводе выведите, скольким отрезкам она принадлежит.</p>
 * <p>
 * Sample Input:<br/>
 * 2 3<br/>
 * 0 5<br/>
 * 7 10<br/>
 * 1 6 11<br/>
 * Sample Output:<br/>
 * 1 0 0<br/>
 * </p>
 */

public class A_QSort {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataA.txt");
        var instance = new A_QSort();
        var result = instance.getAccessory(stream);
        for (var index : result)
            System.out.print(index + " ");
    }

    /**
     * Тут реализуйте логику задачи с применением быстрой сортировки
     */
    int[] getAccessory(InputStream stream) {
        var scanner = new Scanner(stream);
        var segments = new Segment[scanner.nextInt()];
        var points = new int[scanner.nextInt()];
        var result = new int[points.length];
        for (var i = 0; i < segments.length; i++)
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        for (var i = 0; i < points.length; i++)
            points[i] = scanner.nextInt();
        qsort(segments);
        for (var i = 0; i < points.length; i++)
            for (var j = 0; j < segments.length && points[i] >= segments[j].start; j++)
                if (points[i] <= segments[j].stop)
                    result[i]++;
        return result;
    }

    <T extends Comparable<T>> void qsort(T[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    <T extends Comparable<T>> void qsort(T[] arr, int left, int right) {
        if (left >= right) return;
        var pivot = lomuto(arr, left, right);
        qsort(arr, left, pivot - 1);
        qsort(arr, pivot + 1, right);
    }

    <T extends Comparable<T>> int lomuto(T[] arr, int left, int right) {
        var pivot = arr[right];
        var i = left;
        for (var j = left; j < right; j++)
            if (arr[j].compareTo(pivot) <= 0)
                swap(arr, i++, j);
        swap(arr, i, right);
        return i;
    }

    <T> void swap(T[] arr, int a, int b) {
        var temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Отрезок
     */
    private static class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            if (start > stop) {
                this.start = stop;
                this.stop = start;
            } else {
                this.start = start;
                this.stop = stop;
            }
        }

        @Override
        public int compareTo(Segment o) {
            return this.start == o.start
                    ? Integer.compare(this.stop, o.stop)
                    : Integer.compare(this.start, o.start);
        }
    }
}
