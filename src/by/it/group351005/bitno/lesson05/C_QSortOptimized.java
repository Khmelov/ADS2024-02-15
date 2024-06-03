package by.it.group351005.bitno.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

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
    private class Segment  implements Comparable{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            //подумайте, что должен возвращать компаратор отрезков
            Segment other = (Segment) o;

            if (this.start < other.start)
                return -1;
            else if (this.start > other.start)
                return 1;
            else {
                if (this.stop < other.stop)
                    return -1;
                else if (this.stop > other.stop)
                    return 1;
                else
                    return 0;
            }
        }
    }


    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments=new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор
        // Сортируем отрезки по начальной точке
        quickSort(segments, 0, segments.length - 1);

        // Ищем подходящие отрезки для каждой точки
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;

            // Бинарный поиск первого отрезка, содержащего точку
            int left = 0;
            int right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (segments[mid].start <= point && point <= segments[mid].stop) {
                    count++;
                    left++;
                    break;
                } else if (segments[mid].start > point) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // Поиск остальных подходящих отрезков
            if (count > 0) {
                for (int j = left; j < n; j++) {
                    if (segments[j].start <= point && point <= segments[j].stop)
                        count++;
                    else
                        break;
                }
            }

            result[i] = count;
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
    private static void quickSort(Segment[] segments, int low, int high) {
        if (low < high) {
            int pi = partition(segments, low, high);

            quickSort(segments, low, pi - 1);
            quickSort(segments, pi + 1, high);
        }
    }

    private static int partition(Segment[] segments, int low, int high) {
        int middle = low + (high - low) / 2;
        Segment pivot = segments[middle];
        Segment temp = segments[middle];
        segments[middle] = segments[high];
        segments[high] = temp;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (segments[j].compareTo(pivot) < 0) {
                i++;
                temp = segments[i];
                segments[i] = segments[j];
                segments[j] = temp;
            }
        }
        temp = segments[i + 1];
        segments[i + 1] = segments[high];
        segments[high] = temp;
        return i + 1;
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
