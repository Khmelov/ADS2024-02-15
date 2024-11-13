package by.it.group310902.najmiddinzoda.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!  НАЧАЛО ЗАДАЧИ  !!!!!!!!!!!!!!!!!!!!!!!!!
        // Читаем количество элементов (n) из ввода
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        // Массив для хранения длин LIS, инициализируем каждый элемент значением 1
        int[] a = new int[n];
        int result = 0;
        // Перебираем все элементы массива m
        for (int i = 0; i < n; i++) {
            // Инициализируем длину LIS для текущего элемента как 1
            a[i] = 1;
            // Проходим по всем предыдущим элементам, чтобы определить LIS для текущего элемента
            for (int j = 0; j < i; j++) {
                // Если текущий элемент меньше или равен предыдущему и LIS для предыдущего элемента (dp[j]) увеличивает LIS для текущего элемента
                if (m[i] <= m[j] && a[j] + 1 > a[i]) {
                    // Обновляем длину LIS для текущего элемента
                    a[i] = a[j] + 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (result < a[i]) {
                result = a[i];
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}