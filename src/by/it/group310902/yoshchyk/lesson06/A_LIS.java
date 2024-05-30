package by.it.group310902.yoshchyk.lesson06;

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
        //новый массив lenArr из целых чисел с длиной, равной длине массива m.
        // Массив lenArr будет хранить длины возрастающих подпоследовательностей в массиве m.
        int[] lenArr = new int[m.length];
        //Инициализация все элементы массива lenArr значением 1.
        for (int i = 0; i < m.length; i++)
            lenArr[i] = 1;
        //поиск самой длинной последовательности
        for (int i = 0; i < lenArr.length; i++){
            for (int j = 0; j < i; j++) {
                if (m[j] < m[i] && lenArr[i] <= lenArr[j])
                    lenArr[i] = lenArr[j] + 1;
            }
        }
        //нахождение длины максимальной подпоследовательности
        int maxLen = lenArr[0];
        for (int i = 1; i < lenArr.length; i++){
            if (lenArr[i] > maxLen) {
                maxLen = lenArr[i];
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return maxLen;
    }
}
