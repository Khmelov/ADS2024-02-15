package by.it.group310901.yakovtsov.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9    //1 6 3 7 6 3 6 3 2 7 8 3 6 4 3 6 ;0+0 0+0 |
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        int invAm = 0;
        LinkedList<LinkedList<Integer>> parts = new LinkedList<LinkedList<Integer>>();
        LinkedList<Integer> curPart = new LinkedList<Integer>();
        curPart.add(a[0]);
        parts.add(curPart);
        for (int i = 1; i < n; i++) {
            curPart = parts.getLast();
            if (curPart.getLast() < a[i]) {
                curPart.add(a[i]);
            }
            else {
                curPart = new LinkedList<Integer>();
                curPart.add(a[i]);
                parts.add(curPart);
            }
        }
        LinkedList<LinkedList<Integer>> newParts;
        LinkedList<Integer> merge1;
        LinkedList<Integer> merge2;
        Integer it1;
        Integer it2;
        while (parts.size() > 1) {
            newParts = new LinkedList<LinkedList<Integer>>();
            if (parts.size() % 2 == 1) {
                newParts.add(parts.getLast());
                parts.removeLast();
            }
            while (!parts.isEmpty()) {
                merge1 = parts.getFirst();
                parts.removeFirst();
                merge2 = parts.getFirst();
                parts.removeFirst();
                curPart = new LinkedList<Integer>();
                while (!merge1.isEmpty() && !merge2.isEmpty()) {
                    it1 = merge1.getFirst();
                    it2 = merge2.getFirst();
                    if (it1 < it2) {
                        curPart.add(it1);
                        merge1.removeFirst();
                    }
                    else {
                        curPart.add(it2);
                        merge2.removeFirst();
                        invAm++;
                    }
                }
                if (!merge1.isEmpty()) {
                    curPart.addAll(merge1);
                }
                if (!merge2.isEmpty()) {
                    curPart.addAll(merge2);
                }
                newParts.add(curPart);
            }
            parts = newParts;
        }






        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return invAm;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
