package by.it.group310902.rubtsova.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая возрастающая подпоследовательность
см.     https://ru.wikipedia.org/wiki/Задача_поиска_наибольшей_увеличивающейся_подпоследовательности
        https://en.wikipedia.org/wiki/Longest_increasing_subsequence

Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    где каждый элемент A[i[k]] больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]<A[i[j+1]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    1 3 3 2 6

    Sample Output:
    3
*/

public class A_LIS {
    // Метод для определения длины наибольшей возрастающей подпоследовательности (LIS)
    int getSeqSize(InputStream stream) throws FileNotFoundException {
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
                // Если текущий элемент (m[i]) больше предыдущего (m[j]) и LIS для предыдущего элемента (dp[j]) увеличивает LIS для текущего элемента
                if (m[j] < m[i] && dp[j] + 1 > dp[i]) {
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
        InputStream stream = A_LIS.class.getResourceAsStream("dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}