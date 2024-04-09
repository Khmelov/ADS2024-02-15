package by.it.group351002.stepanenko.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class A_QSort {

    // Отрезок
    private static class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            // Сортируем отрезки по возрастанию начальной точки
            return Integer.compare(this.start, o.start);
        }
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

        // Сортируем отрезки по возрастанию начальной точки
        Arrays.sort(segments);

        // Для каждой точки находим количество отрезков, которым она принадлежит
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (Segment segment : segments) {
                if (segment.start <= points[i] && points[i] <= segment.stop) {
                    count++;
                } else if (segment.start > points[i]) {
                    break;
                }
            }
            result[i] = count;
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}