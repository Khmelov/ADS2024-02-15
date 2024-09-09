package by.it.group310902.kulik.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileInputStream;
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
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            return (this.start == o.start?this.stop-o.stop : this.start-o.start);
        }
    }
    int partition(Segment[] a, int l, int r){
        int m = (l + r)/2;
        Segment mid = a[m];
        int i = l;
        int j = r;
        while(i<=j){
            while(a[i].compareTo(mid)<0)
                i++;

            while(a[j].compareTo(mid)>=0 && j>l)
                j--;

            if(i>=j)
                break;

            Segment t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        return j;
    }
    void quickSort(Segment[] a, int l, int r){
        if (l < r){
            int q1 = partition(a, l, r);
            int q2 = partition(a,q1,r);
            quickSort(a, l, q1);
            quickSort(a, q1+1, q2);
            quickSort(a,q2+1, r);
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

        for (int i = 0; i < m; i++) {
            int c = 0;
            int l = 0;
            int r = n - 1;

            while (l < r) {
                int mid = (l + r)/ 2;
                if (points[i] >= segments[mid].start && points[i] <= segments[mid].stop) {
                    c++;
                    break;
                }
                if (points[i] < segments[mid].start)
                    r = m - 1;
                else
                    l = m + 1;
            }

            for (int j = l+1; j < n; j++) {
                if (points[i] >= segments[j].start && points[i] <= segments[j].stop)
                    c++;
            }

            result[i] = c;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310902/kulik/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
