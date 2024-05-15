package by.it.group310901.chizh.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private void quickSort(Segment[] segments, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            int m1 = partition(segments, left, mid);
            int m2 = partition(segments, m1 + 1, right);
            quickSort(segments, left, m1 - 1);
            quickSort(segments, m1 + 1,m2 );
            quickSort(segments, m2 + 1, right);
        }
    }
    private int partition(Segment[] segments, int left, int right) {
        Segment x = segments[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (segments[i].compareTo(x) <= 0) {
                j++;
                Segment tmp = segments[j];
                segments[j] = segments[i];
                segments[i] = tmp;
            }
        }
        Segment tmp = segments[j];
        segments[j] = segments[left];
        segments[left] = tmp;
        return j;
    }
    private int binarySearch(int pos, Segment[] segments){
        // find in the arrays of segments
        int left = 0;
        int right = segments.length - 1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (pos < segments[mid].start){
                right = mid - 1;
            } else if (pos > segments[mid].stop){
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    //отрезок
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            if(start > stop){
                this.start = stop;
                this.stop = start;
            }else{
                this.start = start;
                this.stop = stop;
            }
        }

        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            if(this.start > o.start){
                return 1;
            }
            if(this.start < o.start){
                return -1;
            }
            return 0;
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


        quickSort(segments, 0, n-1);
        for(int i = 0 ; i < m; i++){
            int point1 = points[i];
            int pos = binarySearch(point1, segments);
            if(pos == -1){
                result[i] = 0;
            } else {
                int count = 1;
                for(int j = pos + 1; j < n; j++){
                    if(segments[j].start <= point1 && segments[j].stop >= point1){
                        count++;
                    } else {
                        break;
                    }
                }
                result[i] = count;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
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
