package by.it.group351002.yarakhovich.lesson06;

import java.io.FileInputStream;
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
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // Общая длина последовательности
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }
        // Создаем массив для хранения длин наибольших кратных подпоследовательностей
        int[] lcsLengths = new int[n];
        // Изначально все элементы имеют длину 1
        for (int i = 0; i < n; i++) {
            lcsLengths[i] = 1;
        }
        // Находим длины LCS для каждого элемента
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[i] % sequence[j] == 0 && lcsLengths[j] + 1 > lcsLengths[i]) {
                    lcsLengths[i] = lcsLengths[j] + 1;
                }
            }
        }
        // Находим максимальную длину LCS
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            if (lcsLengths[i] > maxLength) {
                maxLength = lcsLengths[i];
            }
        }
        // Возвращаем максимальную длину кратной подпоследовательности
        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }
}