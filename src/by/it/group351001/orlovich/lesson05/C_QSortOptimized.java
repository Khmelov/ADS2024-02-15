package by.it.group351001.orlovich.lesson05;

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
            return 0;
        }
    }

    private int partition(Segment[] bufSegment, int low, int high){
        int middle = low + (high - low) / 2;
        Segment mainSegment = bufSegment[middle];
        Segment tempSegment = bufSegment[middle];
        bufSegment[middle] = bufSegment[high];
        bufSegment[high] = tempSegment;
        int i = low - 1;
        for (int j = low; j < high; j++){
            if (bufSegment[j].start <= mainSegment.start && bufSegment[j].stop <= mainSegment.stop){
                i++;
                tempSegment = bufSegment[i];
                bufSegment[i] = bufSegment[j];
                bufSegment[j] = tempSegment;

            }
        }
        tempSegment = bufSegment[i + 1];
        bufSegment[i + 1] = bufSegment[high];
        bufSegment[high] = tempSegment;
        return i + 1;
    }
    void quicksort(Segment[] seg, int low, int high){
        if (low <= high){
            int temp = partition(seg, low, high);
            quicksort(seg, low, temp - 1);
            quicksort(seg, temp + 1, high);
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

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
        quicksort(segments, 0, segments.length - 1);
        for (int i = 0; i < m; i++){
            int point = points[i];
            int pointcount = 0;
            int left = 0;
            int right = n - 1;
            boolean exitflag = false;
            while ((left < right) &&  !exitflag){
                int middle = left + (right - left) / 2;
                if (point >= segments[middle].start && point <= segments[middle].stop) {
                    pointcount = 1;
                    exitflag = true;
                }
                else if (point < segments[middle].start) {
                    right = middle - 1;
                }
                else {
                    left = middle + 1;
                }
            }
            for (int j = left+1; j < segments.length; j++) {
                if (point >= segments[j].start && point <= segments[j].stop)
                    pointcount = 1;
            }
            result[i] = pointcount;


        }


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

}
