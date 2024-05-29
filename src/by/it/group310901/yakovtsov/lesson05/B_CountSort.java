package by.it.group310901.yakovtsov.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class  B_CountSort {


    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        int[] points=new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом
        LinkedList<Integer>[] nums = new LinkedList[10];
        for (int i = 0; i < 10; i++) {
            nums[i] = new LinkedList<>();
        }
        for (int i = 0; i < n; i++) {
            nums[points[i] % 10].add(points[i]);
        }
        int iter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < nums[i].size(); j++) {
                points[iter] = nums[i].get(j);
                iter++;
            }
            nums[i] = new LinkedList<>();
        }
        for (int i = 0; i < n; i++) {
            nums[points[i] / 10].add(points[i]);
        }
        iter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < nums[i].size(); j++) {
                points[iter] = nums[i].get(j);
                iter++;
            }
            nums[i] = new LinkedList<>();
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
