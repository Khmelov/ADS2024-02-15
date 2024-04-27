package by.it.group351002.alexeichik.lesson05;

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
    static int partition(Segment[] arr, int begin, int end) {
        int middle = begin+(end-begin)/2;
         Segment pivot = arr[middle];
        Segment temp = arr[middle];
        arr[middle] = arr[end];
        arr[end] = temp;
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j].start<pivot.start || arr[j].start == pivot.start && arr[j].stop < pivot.stop){
                i++;
               if (!(arr[j].start == pivot.start && arr[j].stop < pivot.stop)) {
               Segment swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;}
            }
        }

       Segment swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        return i+1;
    }
    static void quickSort(Segment[] arr, int begin, int end) {
        if (begin < end) {
            int pos = partition(arr,begin, end);
            quickSort(arr, begin, pos -1);
            quickSort(arr,  pos+ 1, end);
        }
    }

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
        quickSort(segments,0,n-1);

        for (int i = 0; i < points.length; i++) {

            int cur=((n)/2)-1;
            int pred=n-1;

            while (Math.abs(pred-cur)>1){

                int h=Math.abs(pred-cur)/2;
                pred=cur;
                if (segments[cur].start>points[i]) cur=cur+h;
                if (segments[cur].start<points[i]) cur=cur-h;
            }
            for (int j=0;j<=cur;j++){
                if (segments[j].stop>=points[i]){
                    result[i]++;
                }
            }


        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    //отрезок
    private class Segment implements Comparable {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            //подумайте, что должен возвращать компаратор отрезков
            return 0;
        }
    }

}
