package by.it.group310901.navrosuk.lesson06;
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
        // Подключаем файл с данными для чтения
        InputStream stream = A_LIS.class.getResourceAsStream("dataA.txt");
        A_LIS instance = new A_LIS();
        // Получаем результат
        int result = instance.getSeqSize(stream);
        // Печатаем результат
        System.out.print(result);
    }

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // Общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Массив для хранения длин наибольших возрастающих подпоследовательностей
        int[] c = new int[n];

        // Заполняем массив длин
        for (int i = 0; i < n; i++) {
            // Изначально длина подпоследовательности для каждого элемента равна 1
            c[i] = 1;
            // Проверяем все предыдущие элементы
            for (int j = 0; j < i; j++) {
                // Если предыдущий элемент меньше текущего и длина подпоследовательности с этим элементом больше текущей,
                // обновляем длину
                if (m[j] < m[i] && c[j] + 1 > c[i]) {
                    c[i] = c[j] + 1;
                }
            }
        }

        // Находим максимальную длину подпоследовательности
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (result < c[i]) {
                result = c[i];
            }
        }
        // Возвращаем результат
        return result;
    }
}