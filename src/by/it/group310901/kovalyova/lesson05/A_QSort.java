package by.it.group310901.kovalyova.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

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

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_QSort.class.getResourceAsStream("dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
    Segment[] qsortfun(int start, int finish,Segment[] m)
    {
        Segment tmp;
        Segment mid=m[(finish+start) / 2];
        int left=start;
        int right=finish;
        while(left<=right)
        {
            while(m[left].compareTo(mid) < 0)
            {
                left++;
            }
            while(m[right].compareTo(mid) > 0)
            {
                right--;
            }
            tmp=m[left];
            m[left]=m[right];
            m[right]=tmp;
            left++;
            right--;
        }
        if (right > start) {
            m = qsortfun(start, right, m);
        }
        if(left<finish) {
            m=qsortfun(left, finish, m);
        }
        return m;
    }

    int[] getAccessory(InputStream stream) {

        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!НАЧАЛО ЗАДАЧИ!!!!!!!!!!!!!!!!!!!!!!!!!
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
        segments=qsortfun(0,n-1,segments);
        for(int i=0;i<m;i++)
        {
            int counter = 0;
            for (Segment segment: segments)
            {
                if (segment.start <= points[i] && points[i] <= segment.stop)
                {
                    counter++;
                }
                else if (segment.start > points[i])
                {
                    break;
                }
            }
            result[i] = counter;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    //отрезок
    private static class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            if (start > stop) {
                int temp = start;
                start = stop;
                stop = temp;
            }
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

}
