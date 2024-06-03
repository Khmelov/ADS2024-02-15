package by.it.group351002.khmel.lesson06;

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
/* найти длину самой длинной возрастающей подпоследовательности */

public class A_LIS {


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
       /* инициализируется для хранения длины самой длинной
        возрастающей подпоследовательности, заканчивающейся
        каждым индексом*/
        int result = 0;
        int[] seq = new int[n];

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        /* в цикле Для каждого индекса i внутренний цикл перебирает
         все j предыдущие индексы i*/
        for (int i = 0; i < n; i++) {
            seq[i] = 1;
            for (int j = 0; j < i; j++) {
                /* условие гарантирует, что элемент по индексу j МЕНЬШЕ элемента
                по индексу i предыдущего индекса. Это необходимо, поскольку мы хотим найти возрастающую
                подпоследовательность

               и превышает ли длина, заканчивающуюся на index j плюс 1
                (путем включения элемента m[i]), текущую длину LIS, заканчивающуюся на index i*/
                if (m[j] < m[i] && seq[j] + 1 > seq[i]) {
                    seq[i] = seq[j] + 1;
                }
            } /*сформировать более длинную возрастающую подпоследовательность*/
            result = Math.max(result, seq[i]);
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
