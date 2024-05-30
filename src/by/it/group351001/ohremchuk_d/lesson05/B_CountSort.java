package by.it.group351001.ohremchuk_d.lesson05;

import java.io.FileInputStream;
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

    int[] countSort(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // считываем размер массива
        int n = scanner.nextInt();
        // массив для хранения исходных данных
        int[] points = new int[n];
        // массив для подсчета частоты чисел от 1 до 10
        int[] numbers = new int[10];

        // инициализация массива частот нулями
        for (int i = 0; i < 10; i++) {
            numbers[i] = 0;
        }

        // читаем числа и заполняем массив частот
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
            numbers[points[i] - 1]++;  // увеличиваем счетчик для числа points[i]
        }

        // создаем индекс для заполнения исходного массива отсортированными значениями
        int k = 0;
        // проходим по массиву частот
        for (int i = 0; i < 10; i++) {
            // пока есть элементы в numbers[i]
            while (numbers[i] > 0) {
                // заполняем исходный массив элементами i+1 (так как индексация с 0)
                points[k] = i + 1;
                k++;
                numbers[i]--;  // уменьшаем счетчик в массиве частот
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // возвращаем отсортированный массив
        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // путь к файлу с данными
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        // получение результата сортировки
        int[] result = instance.countSort(stream);
        // вывод результата
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
