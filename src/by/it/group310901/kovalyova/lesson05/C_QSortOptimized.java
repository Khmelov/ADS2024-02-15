package by.it.group310901.kovalyova.lesson05;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Видеорегистраторы и площадь 2.</p>
 * <p>Условие то же что и в задаче А.</p>
 * <p>По сравнению с задачей A доработайте алгоритм так, чтобы</p>
 * <ol>
 * <li>
 * <p>он оптимально использовал время и память</p>
 * <ul>
 * <li>за стек отвечает элиминация хвостовой рекурсии,</li>
 * <li>за сам массив отрезков - сортировка на месте</li>
 * <li>рекурсивные вызовы должны проводиться на основе 3-разбиения</li>
 * </ul>
 * </li>
 * <li>при поиске подходящих отрезков для точки реализуйте метод бинарного поиска для первого отрезка решения, а
 * затем найдите оставшуюся часть решения (т.е. отрезков, подходящих для точки, может быть много)</li>
 * </ol>
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

public class C_QSortOptimized {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataC.txt");
        var instance = new C_QSortOptimized();
        var result = instance.getAccessory2(stream);
        for (var index : result)
            System.out.print(index + " ");
    }

    /**
     * Тут реализуйте логику задачи с применением быстрой сортировки
     */
    int[] getAccessory2(InputStream stream) {
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
            for (
                    var j = find(segments, points[i]);
                    j != -1 && j < segments.length && points[i] >= segments[j].start;
                    j++
            )
                if (points[i] <= segments[j].stop) result[i]++;
        return result;
    }

    <T extends Comparable<T>> void qsort(T[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    <T extends Comparable<T>> void qsort(T[] arr, int left, int right) {
        if (left >= right) return;
        var pivot = hoare(arr, left, right);
        qsort(arr, left, pivot - 1);
        qsort(arr, pivot + 1, right);
    }

    <T extends Comparable<T>> int hoare(T[] arr, int left, int right) {
        var pivot = arr[right];
        while (true) {
            while (arr[left].compareTo(pivot) < 0) left++;
            while (arr[right].compareTo(pivot) > 0) right--;
            if (left >= right) return right;
            swap(arr, left, right);
        }
    }

    <T> void swap(T[] arr, int a, int b) {
        var temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;

    }
    int find(Segment[] arr, int target) {
        return find(arr, target, 0, arr.length - 1);
    }

    int find(Segment[] arr, int target, int left, int right) {
        return find(arr, target, left, (left + right) / 2, right);
    }

    int find(Segment[] arr, int target, int left, int mid, int right) {
        return (left > right)
                ? -1
                : arr[mid].compareTo(target) < 0
                ? find(arr, target, mid + 1, right)
                : arr[mid].compareTo(target) > 0
                ? find(arr, target, left, mid - 1)
                : mid;
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
            return this.start == o.start ? Integer.compare(this.stop, o.stop) : Integer.compare(this.start, o.start);
        }

        public int compareTo(int o) {
            return o < start ? -1 : stop < o ? 1 : 0;
        }
    }
}