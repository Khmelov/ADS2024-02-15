package by.it.group310902.sverchkov.lesson05;

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
        InputStream stream = C_QSortOptimized.class.getResourceAsStream("dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
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

        sort(segments);

        for (int i = 0; i < points.length; i++) {
            int j = 0;
            int k = 0;
            int idx = binaryFind(segments, points[i]);
            if (idx < 0) continue;
            while (idx < segments.length && segments[idx].start <= points[i] && segments[idx].stop >= points[i]){
                idx++;
                k++;
            }
            result[i] = k;
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    void sort(Segment[] segments) {
        quickSort(segments, 0, segments.length - 1);
    }

    void quickSort(Segment[] segments, int l, int r) {
        if (l >= r) return;
        if (r - l < 4) {
            int pivot = partOfQuickSort(segments, l, r);
            quickSort(segments, l, pivot - 1);
            quickSort(segments, pivot, r);
        } else {
            int mid1 = (r - l) / 3 + l;
            int mid2 = 2 * (r - l) / 3 + l;
            quickSort(segments, l, mid1);
            quickSort(segments, mid1 + 1, mid2);
            quickSort(segments, mid2 + 1, r);
        }
    }

    int partOfQuickSort(Segment[] segments, int l, int r) {
        int pivot = (l + r) / 2;
        while (l <= r) {
            while (segments[l].compareTo(segments[pivot]) < 0) l++;
            while (segments[r].compareTo(segments[pivot]) > 0) r--;
            if (l <= r) {
                Segment temp = segments[l];
                segments[l] = segments[r];
                segments[r] = segments[l];
                l++;
                r--;
            }
        }
        return l;
    }

    public int binaryFind(Segment[] a, int value) {
        boolean found = false;
        int l = 0;
        int r = a.length - 1;
        int mid = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (a[mid].start <= value && a[mid].stop >= value) {
                found = true;
                break;
            }
            if (a[mid].start > value) {
                r = mid - 1;
            } else l = mid + 1;
        }
        if (found){
            while (mid >= 0 && a[mid].start <= value && a[mid].stop >= value){
                mid--;
            }
            return ++mid;
        }
        return -1;
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
            return this.start == o.start ? this.stop - o.stop : this.start - o.start;
        }
    }

}
