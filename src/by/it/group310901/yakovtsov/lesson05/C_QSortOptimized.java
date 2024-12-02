package by.it.group310901.yakovtsov.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
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
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            return start - o.start;
        }
    }

    void sortElems(LinkedList<Segment> arr) {
        if (arr.size() > 1) {
            LinkedList<Segment> equal = new LinkedList<>();
            LinkedList<Segment> bigger = new LinkedList<>();
            LinkedList<Segment> smaller = new LinkedList<>();
            equal.add(arr.removeFirst());
            Segment compSegm = equal.getFirst();
            while (!arr.isEmpty()) {
                int diff = compSegm.compareTo(arr.getFirst());
                if (diff > 0) {
                    smaller.add(arr.removeFirst());
                }
                else if (diff < 0) {
                    bigger.add(arr.removeFirst());
                }
                else {
                    equal.add(arr.removeFirst());
                }
            }
            sortElems(bigger);
            sortElems(smaller);
            arr.addAll(smaller);
            arr.addAll(equal);
            arr.addAll(bigger);
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        //Segment[] segments=new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        //читаем сами отрезки
        LinkedList<Segment> arr = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            arr.add(new Segment(scanner.nextInt(), scanner.nextInt()));
        }
        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор
        sortElems(arr);
        for (int i = 0; i < m; i++) {
            int freq = 0;
            int stop = n-1;
            if (points[i] < arr.get(stop).start) {
                int start = 0;
                if (n > 2) {
                    while (stop - start > 1) {
                        int mid = (start + stop) / 2;
                        if (points[i] > arr.get(mid).start) {
                            start = mid;
                        } else {
                            stop = mid;
                        }
                    }
                }
                else {
                    if (arr.get(stop).start > points[i]) {
                        if (arr.get(start).start >= points[i]) {
                            stop = -1;
                        }
                        else {
                            stop = start;
                        }
                    }
                }
            }
            for (int j = 0; (j <= stop); j++) {
                if (arr.get(j).stop >= points[i]) {
                    freq++;
                }
            }
            result[i] = freq;
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
