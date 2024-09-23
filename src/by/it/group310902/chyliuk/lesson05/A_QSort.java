package by.it.group310902.chyliuk.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

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

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        // Создаем сканнер для чтения данных из потока ввода
        Scanner scanner = new Scanner(stream);

        // Читаем количество отрезков (n) и количество точек (m)
        int n = scanner.nextInt(); // Количество отрезков
        Segment[] segments = new Segment[n]; // Создаем массив отрезков
        int m = scanner.nextInt(); // Количество точек
        int[] points = new int[m]; // Создаем массив точек
        int[] result = new int[m]; // Создаем массив для хранения результата

        // Читаем координаты начала и конца каждого отрезка и сохраняем их в массив отрезков
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // Читаем координаты каждой точки и сохраняем их в массив точек
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем массив отрезков по начальным координатам
        Arrays.sort(segments);

        // Проходим по каждой точке и проверяем, сколько отрезков она пересекает
        for (int i = 0; i < m; i++) {
            for (Segment segment : segments) {
                // Если точка находится внутри отрезка, увеличиваем счетчик
                if (points[i] >= segment.start && points[i] <= segment.stop) {
                    result[i]++;
                }
                // Если точка лежит перед текущим отрезком, выходим из цикла
                else if (points[i] < segment.start) {
                    break;
                }
            }
        }

        // Возвращаем массив с результатом
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
