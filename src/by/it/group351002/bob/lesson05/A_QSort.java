package by.it.group351002.bob.lesson05;

import java.io.FileInputStream;
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

    //отрезок
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            if (start<=stop) {
                this.start = start;
                this.stop = stop;
            }else{
                this.start = stop;
                this.stop = start;
            }
            //тут вообще-то лучше доделать конструктор на случай если
            //концы отрезков придут в обратном порядке
        }

        @Override
        public int compareTo(Segment o) {
            if ( this.stop < o.stop ) return -1;
            else if ( this.stop == o.stop ) return 0;
            else return 1;

        }
    }
    static void qSort(Segment[] a, int l, int r){
        if (l<r){
            int m = (int) (Math.random()*(r-l+1)+l);

            int cur = l;
            Segment num = a[m];

            Segment temp = a[l];
            a[l] = a[m];
            a[m] = temp;

            for (int i =l+1; i<=r; i++){
                if (a[i].start<=num.start){
                    temp = a[i];
                    a[i] = a[++cur];
                    a[cur] = temp;
                }
            }

            temp = a[l];
            a[l] = a[cur];
            a[cur] = temp;

            m = cur;

            qSort(a, l, m-1);
            qSort(a, m+1, r);
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
/*Чтение входных данных:
Программа считывает число отрезков n, число точек m и координаты каждого отрезка и каждой точки.
Отрезки представлены в виде пар координат start и stop,
обозначающих начало и конец каждого отрезка соответственно.
Точки представлены одним целочисленным значением.

Быстрая сортировка отрезков: Программа использует алгоритм быстрой сортировки для отрезков,
чтобы упорядочить их по координате конца отрезка (stop).
Это делается с помощью метода qSort,
который случайным образом выбирает опорный элемент и
разделяет массив на две части - с элементами меньше опорного и
с элементами больше опорного.
Затем процесс повторяется для обеих частей рекурсивно до тех пор,
пока весь массив не будет отсортирован.

Подсчёт принадлежности точек отрезкам:
После сортировки отрезков, для каждой точки проверяется,
скольким отрезкам она принадлежит.
Для этого используется цикл, в котором для каждой точки проверяется,
находится ли она внутри каждого отрезка.
Если точка попадает внутрь отрезка или на его границу
(то есть, если координата точки находится между start и stop отрезка),
то счётчик для этой точки увеличивается.

Вывод результата:
После подсчёта принадлежности каждой точки к отрезкам,
результат выводится в консоль.
Каждое число в выводе соответствует количеству отрезков,
к которым принадлежит соответствующая точка.*/

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
        qSort(segments, 0, segments.length-1);

        for(int k = 0; k < points.length; k++){
            for (int i = 0; i<segments.length && segments[i].start<=points[k]; i++){
                if (segments[i].stop>=points[k]){
                    result[k]++;
                }
            }
        }


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
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
