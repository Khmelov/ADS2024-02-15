package by.it.group351003.lutai.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.
При сортировке реализуйте метод со сложностью O(n)
Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_CountSort.class.getResourceAsStream("dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом

        //найдём максимальный элемент массива
        int max = points[0];
        for (int k: points) {
            if (k > max) max = k;
        }

        //инициализируем нулями вспомогательный массив
        int[] tempPoints = new int[max+1];
        for (int i = 0; i < max+1; i++) {
            tempPoints[i] = 0;
        }

        //посчитаем во вспомогательном массиве количество каждого из элементов
        for (int k: points) tempPoints[k]++;

        //проходимся по вспомогательному массиву и заполняем массив points подряд, вставляя по порядку tempPoints[i] значений i
        int k = 0;
        for (int i = 0; i < tempPoints.length; i++) {
            for (int j = 0; j < tempPoints[i]; j++) {
                points[k] = i;
                k++;
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return points;
    }
}