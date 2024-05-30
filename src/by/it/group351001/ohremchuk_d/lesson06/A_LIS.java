package by.it.group351001.ohremchuk_d.lesson06;

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
        int[] m = new int[n];

        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Вычисляем длину наибольшей возрастающей подпоследовательности
        int result = longestIncreasingSubsequenceLength(m);

        return result;
    }

    // Реализация метода поиска длины наибольшей возрастающей подпоследовательности
    // с временной сложностью O(nlogn)
    int longestIncreasingSubsequenceLength(int numbers[]) {

        // Если массив содержит 1 или 0 элементов, то его длина является наибольшей возрастающей подпоследовательностью
        if (numbers.length <= 1) {
            return numbers.length;
        }

        // Инициализируем длину LIS
        int lis_length = -1;

        // Массив для хранения текущей наибольшей возрастающей подпоследовательности
        int subsequence[] = new int[numbers.length];
        // Массив для хранения индексов конца подпоследовательностей
        int indexes[] = new int[numbers.length];

        // Заполняем массив значениями, превышающими все возможные значения элементов массива
        for (int i = 0; i < numbers.length; ++i) {
            subsequence[i] = Integer.MAX_VALUE;
        }

        // Первое значение подпоследовательности равно первому элементу исходного массива
        subsequence[0] = numbers[0];
        indexes[0] = 0;

        // Проходим по всем элементам исходного массива
        for (int i = 1; i < numbers.length; ++i) {
            // Ищем индекс, куда можно вставить текущий элемент
            indexes[i] = ceilIndex(subsequence, 0, i, numbers[i]);

            // Обновляем длину LIS, если текущий индекс больше предыдущего
            if (lis_length < indexes[i]) {
                lis_length = indexes[i];
            }
        }

        // Возвращаем длину наибольшей возрастающей подпоследовательности
        return lis_length + 1;
    }

    // Функция для нахождения индекса вставки элемента в текущую подпоследовательность
    int ceilIndex(int subsequence[], int startLeft, int startRight, int key) {

        int mid = 0;
        int left = startLeft;
        int right = startRight;
        int ceilIndex = 0;
        boolean ceilIndexFound = false;

        // Используем двоичный поиск для нахождения места вставки
        for (mid = (left + right) / 2; left <= right && !ceilIndexFound; mid = (left + right) / 2) {
            if (subsequence[mid] > key) {
                right = mid - 1;
            } else if (subsequence[mid] == key) {
                ceilIndex = mid;
                ceilIndexFound = true;
            } else if (mid + 1 <= right && subsequence[mid + 1] >= key) {
                subsequence[mid + 1] = key;
                ceilIndex = mid + 1;
                ceilIndexFound = true;
            } else {
                left = mid + 1;
            }
        }

        // Если индекс вставки не найден, определяем его
        if (!ceilIndexFound) {
            if (mid == left) {
                subsequence[mid] = key;
                ceilIndex = mid;
            } else {
                subsequence[mid + 1] = key;
                ceilIndex = mid + 1;
            }
        }

        // Возвращаем индекс вставки
        return ceilIndex;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Путь к файлу с входными данными
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");

        // Создаем экземпляр класса и вызываем метод для нахождения длины LIS
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);

        // Выводим результат
        System.out.print(result);
    }
}
