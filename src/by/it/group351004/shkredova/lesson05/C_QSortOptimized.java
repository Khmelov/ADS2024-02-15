package by.it.group351004.shkredova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;
/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
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

    //отрезок
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

    // Метод для получения решения задачи
    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream); // Создание сканера для ввода данных
        int n = scanner.nextInt(); // Чтение количества отрезков
        Segment[] segments=new Segment[n]; // Создание массива отрезков
        int m = scanner.nextInt(); // Чтение количества точек
        int[] points=new int[m];
        int[] result=new int[m];

        for (int i = 0; i < n; i++) {
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }

        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }

        Arrays.sort(segments);

        // Обработка каждой точки
        for (int i = 0; i < m; i++) {
            int point = points[i]; // Получение текущей точки
            int left = 0, right = n - 1; // Инициализация границ для бинарного поиска
            while (left <= right) { // Пока левая граница не больше правой
                int mid = left + (right - left) / 2; // Находим середину
                if (segments[mid].start <= point && point <= segments[mid].stop) { // Если точка внутри отрезка
                    result[i]++; // Увеличиваем счетчик
                    int j = mid; // Создаем индекс для движения по массиву отрезков
                    while (--j >= 0 && segments[j].start <= point && point <= segments[j].stop) {
                        result[i]++; // Перемещаемся влево и увеличиваем счетчик
                    }
                    j = mid; // Возвращаемся к исходной точке
                    while (++j < n && segments[j].start <= point && point <= segments[j].stop) {
                        result[i]++; // Перемещаемся вправо и увеличиваем счетчик
                    }
                    break;
                } else if (segments[mid].start > point) { // Если точка левее отрезка
                    right = mid - 1; // Сдвигаем правую границу
                } else { // Если точка правее отрезка
                    left = mid + 1; // Сдвигаем левую границу
                }
            }
        }

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
