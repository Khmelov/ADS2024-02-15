package by.it.group310902.chyliuk.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
В первой строке источника данных даны:
        - целое число 1<=n<=100000 (размер массива)
        - сам массив A[1...n] из n различных натуральных чисел,
          не превышающих 10E9, в порядке возрастания,
Во второй строке
        - целое число 1<=k<=10000 (сколько чисел нужно найти)
        - k натуральных чисел b1,...,bk не превышающих 10E9 (сами числа)
Для каждого i от 1 до kk необходимо вывести индекс 1<=j<=n,
для которого A[j]=bi, или -1, если такого j нет.

        Sample Input:
        5 1 5 8 12 13
        5 8 1 23 1 11

        Sample Output:
        3 1 -1 1 -1

(!) Обратите внимание на смещение начала индекса массивов JAVA относительно условий задачи
*/

public class A_BinaryFind {
    int[] findIndex(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream); // Создание объекта Scanner для чтения входных данных

        int n = scanner.nextInt(); // Считывание размера массива
        int[] a = new int[n]; // Создание массива для хранения элементов
        for (int i = 1; i <= n; i++) {
            a[i - 1] = scanner.nextInt(); // Заполнение массива элементами из входных данных
        }

        int k = scanner.nextInt(); // Считывание количества значений для поиска
        int[] result = new int[k]; // Создание массива для хранения результатов
        for (int i = 0; i < k; i++) {
            int value = scanner.nextInt(); // Считывание значения для поиска

            // Бинарный поиск
            int left = 0, right = n - 1; // Инициализация границ поиска
            while (left <= right) {
                int mid = left + (right - left) / 2; // Вычисление среднего индекса
                if (a[mid] == value) {
                    result[i] = mid + 1; // Добавление найденного индекса в результат (+1, так как индексация в задаче начинается с 1)
                    break;
                } else if (a[mid] < value) {
                    left = mid + 1; // Сужение интервала поиска
                } else {
                    right = mid - 1; // Сужение интервала поиска
                }
            }

            if (left > right) {
                result[i] = -1; // Значение не найдено, записываем -1
            }
        }

        return result; // Возвращаем массив с индексами или -1, если значение не найдено
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.findIndex(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
