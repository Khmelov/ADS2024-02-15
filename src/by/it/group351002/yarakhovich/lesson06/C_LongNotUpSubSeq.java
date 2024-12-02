package by.it.group351002.yarakhovich.lesson06;

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
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // Общая длина последовательности
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }
        // Создаем массив для хранения длин наибольших невозрастающих подпоследовательностей
        int[] lisLengths = new int[n];
        // Изначально все элементы имеют длину 1
        for (int i = 0; i < n; i++) {
            lisLengths[i] = 1;
        }
        // Находим длины LIS для каждого элемента
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[j] >= sequence[i] && lisLengths[j] + 1 > lisLengths[i]) {
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
        // Возвращаем длину наибольшей невозрастающей подпоследовательности
        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }
}