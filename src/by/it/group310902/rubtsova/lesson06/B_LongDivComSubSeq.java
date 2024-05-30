package by.it.group310902.rubtsova.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая кратная подпоследовательность

Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] делится на предыдущий
    т.е. для всех 1<=j<k, A[i[j+1]] делится на A[i[j]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    4
    3 6 7 12

    Sample Output:
    3
*/

public class B_LongDivComSubSeq {
    int getDivSeqSize(InputStream stream) throws FileNotFoundException {
        // Создаем сканнер для чтения данных из потока ввода
        Scanner scanner = new Scanner(stream);

        // Читаем количество элементов (n) из ввода
        int n = scanner.nextInt();

        // Создаем массив для хранения элементов
        int[] m = new int[n];

        // Заполняем массив данными из ввода
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Массив для хранения длин LIS, инициализируем каждый элемент значением 1
        int[] dp = new int[n];

        // Переменная для хранения длины наибольшей LIS
        int result = 0;

        // Перебираем все элементы массива m
        for (int i = 0; i < n; i++) {
            // Инициализируем длину LIS для текущего элемента как 1
            dp[i] = 1;

            // Проходим по всем предыдущим элементам, чтобы определить LIS для текущего элемента
            for (int j = 0; j < i; j++) {
                // Если текущий элемент делится на предыдущий без остатка и LIS для предыдущего элемента (dp[j]) увеличивает LIS для текущего элемента
                if (m[i] % m[j] == 0 && dp[j] + 1 > dp[i]) {
                    // Обновляем длину LIS для текущего элемента
                    dp[i] = dp[j] + 1;
                }
            }

            // Обновляем переменную result с учетом текущей длины LIS
            result = Math.max(result, dp[i]);
        }

        // Возвращаем длину наибольшей LIS
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_LongDivComSubSeq.class.getResourceAsStream("dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }
}