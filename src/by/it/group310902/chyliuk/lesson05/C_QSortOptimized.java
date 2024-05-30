package by.it.group310902.chyliuk.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

public class C_QSortOptimized {

    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            } else {
                return Integer.compare(o.stop, this.stop);
            }
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        // Создаем сканнер для чтения данных из потока ввода
        Scanner scanner = new Scanner(stream);

        // Читаем количество сегментов (n) и создаем массив сегментов
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];

        // Читаем количество точек (m)
        int m = scanner.nextInt();

        // Создаем массив для хранения точек и массив для хранения результатов
        int[] points = new int[m];
        int[] result = new int[m];

        // Заполняем массив сегментов данными из ввода
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // Заполняем массив точек данными из ввода
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем массив сегментов
        Arrays.sort(segments);

        // Проходим по каждой точке и ищем количество пересечений с сегментами
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int left = 0, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (segments[mid].start <= point && point <= segments[mid].stop) {
                    // Если точка находится внутри сегмента, увеличиваем счетчик пересечений
                    result[i]++;
                    int j = mid;
                    // Проверяем сегменты слева от найденного сегмента
                    while (--j >= 0 && segments[j].start <= point && point <= segments[j].stop) {
                        result[i]++;
                    }
                    j = mid;
                    // Проверяем сегменты справа от найденного сегмента
                    while (++j < n && segments[j].start <= point && point <= segments[j].stop) {
                        result[i]++;
                    }
                    break;
                } else if (segments[mid].start > point) {
                    // Если точка слева от текущего сегмента, обновляем правую границу поиска
                    right = mid - 1;
                } else {
                    // Если точка справа от текущего сегмента, обновляем левую границу поиска
                    left = mid + 1;
                }
            }
        }

        // Возвращаем массив результатов
        return result;
    }




    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
