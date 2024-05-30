package by.it.group351001.anton_matsiushenka.lesson06;

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


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_LIS.class.getResourceAsStream("dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        int[] dp = new int[n]; // Создаем массив dp размером n, где dp[i] будет
        // содержать максимальную длину возрастающей подпоследовательности, заканчивающейся на элементе m[i].
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Инициализируем значения dp[i] единицами, так как каждый
            // элемент m[i] является подпоследовательностью длины 1.
            for (int j = 0; j < i; j++) {
                // Для каждого элемента m[i] мы проверяем все
                // предыдущие элементы m[j], где j от 0 до i-1.
                // Если m[i] больше m[j] и dp[i] меньше dp[j] + 1,
                // то это означает, что мы можем продолжить возрастающую подпоследовательность,
                // и мы обновляем значение dp[i] на dp[j] + 1, чтобы учесть m[i].
                if ((m[i] > m[j]) && (dp[i] < dp[j] + 1)) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLength) {
                maxLength = dp[i];
            }
        }
        int result = maxLength;
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
}
