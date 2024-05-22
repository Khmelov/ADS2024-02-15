package by.it.group351002.yemelyanenka.lesson06;

import java.io.FileInputStream;
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
    для которой каждый элемент A[i[k]]больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]<A[i[j+1]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    1 3 3 2 6

    Sample Output:
    3
*/

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Общая длина последовательности
        int n = scanner.nextInt();
        int[] sequence = new int[n];

        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }

        // Создаем массив для хранения длин LIS для каждого элемента последовательности
        int[] lisLengths = new int[n];

        // Инициализируем все элементы LIS длиной 1, так как каждый элемент по себе является LIS
        for (int i = 0; i < n; i++) {
            lisLengths[i] = 1;
        }

        // Находим длины LIS для каждого элемента последовательности
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[i] > sequence[j] && lisLengths[i] < lisLengths[j] + 1) {
                    lisLengths[i] = lisLengths[j] + 1;
                }
            }
        }

        // Находим максимальную длину LIS
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            if (lisLengths[i] > maxLength) {
                maxLength = lisLengths[i];
            }
        }

        // Возвращаем длину наибольшей возрастающей подпоследовательности
        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}