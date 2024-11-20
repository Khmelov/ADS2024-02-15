package by.it.group310902.strizhevskiy.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    public static void main(String[] args) throws FileNotFoundException {
        // InputStream stream = C_QSortOptimized.class.getResourceAsStream("dataC.txt");
        // C_QSortOptimized instance = new C_QSortOptimized();
        // int[] result = instance.getAccessory2(stream);
        // for (int index : result) {
        //     System.out.print(index + " ");
        // }

        C_QSortOptimized qs = new C_QSortOptimized();
        Integer[] a = new Integer[10000000];

        for (int i = 0; i < a.length; i++) {
            a[i] = (int)(Math.random()*100000);
        }

        long sortTime = System.currentTimeMillis();
        qs.qsort(a, 0, a.length);
        sortTime = System.currentTimeMillis() - sortTime;

        System.out.println(sortTime);
        for (int i = 0; i < a.length-1; i++) {
            if (a[i].compareTo(a[i+1]) > 0) {
                System.out.println("Wrong");
                break;
            }
        }

    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор

        qsort(segments, 0, segments.length);

        for (int i = 0; i < points.length; i++) {

            int l = 0, r = segments.length-1;
            while (l < r) {
                int mid = (l+r)/2;
                int mcp = segments[mid].stop-points[i];

                if (mcp == 0) break;
                if (mcp < 0) l = mid+1;
                else r = mid-1;
            }

            l = r+1;
            while (l < segments.length)
                if (segments[l++].start <= points[i]) { result[i]++; }

            while (0 <= r && points[i] <= segments[r].stop)
                if (segments[r--].start <= points[i]) { result[i]++; }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    <T extends Comparable<T>> void partition(T[] s, int from, int to, int[] result){
        int l = from, r = to-1, m = (int)(Math.random()*(to-from)+from);
        int lcp = 0, rcp = 0, p = l, q = r;
        T pivot = s[m];

        while (true) {
            while (l <= r && (lcp = s[l].compareTo(pivot)) < 0) { l++; }
            while (l <= r && (rcp = s[r].compareTo(pivot)) > 0) { r--; }

            if (l >= r) { break; }

            //Левые опорные в начало левого массива
            if (lcp == 0) {
                T temp = s[p];
                s[p++] = s[l];
                s[l++] = temp;
                continue;
            }

            //Правые опорные в конец правого массива
            if (rcp == 0) {
                T temp = s[q];
                s[q--] = s[r];
                s[r--] = temp;
                continue;
            }

            T temp = s[l];
            s[l++] = s[r];
            s[r--] = temp;
        }

        r = l;

        //Ставим опорные на своё место
        while (from <= --p) {
            T temp = s[--r];
            s[r] = s[p];
            s[p] = temp;
        }

        while (to > ++q) {
            T temp = s[l];
            s[l++] = s[q];
            s[q] = temp;
        }

        result[0] = r;
        result[1] = l;
    }

    private int[] parts = new int[2];
    <T extends Comparable<T>> void qsort(T[] s, int from, int to){
        while (1 < to-from) {
            partition(s, from, to, parts);
            int r = parts[0];
            int l = parts[1];

            if (r-from < to-l) {
                qsort(s, from, r);
                from = l;
                continue;
            }

            qsort(s, l, to);
            to = r;
        }
    }

    //отрезок
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            return (int) Math.signum(start-o.start);
        }
    }

}
