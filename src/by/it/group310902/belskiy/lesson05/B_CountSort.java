package by.it.group310902.belskiy.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по не убыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    int[] countSort(InputStream stream) {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        int[] points=new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом


        if (points.length == 0)
            return points;

        int min = points[0];
        int max = points[0];

        // Находим минимальное и максимальное значение в массиве
        for (int i = 1; i < points.length; i++)
        {
            if (points[i] < min)
                min = points[i];
            if (points[i] > max)
                max = points[i];
        }

        // Создаем массив для подсчета количества повторений каждого значения
        int[] countArray = new int[max - min + 1];

        /*
         Заполняем countArray
         5 3 9 5 3 6 7 8 2
        */
        for (int point : points) {
            countArray[point - min]++;
        }

        // Восстанавливаем исходный массив
        int index = 0;
        for (int i = 0; i < countArray.length; i++)
        {
            for (int j = 0; j < countArray[i]; j++)
            {
                points[index++] = i + min;
            }
        }


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return points;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result=instance.countSort(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
