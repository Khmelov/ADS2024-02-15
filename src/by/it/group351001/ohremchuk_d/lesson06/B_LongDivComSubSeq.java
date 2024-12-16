package by.it.group351001.ohremchuk_d.lesson06;

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
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int result = longestDivSubsequenceLength(m);


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    int longestDivSubsequenceLength(int numbers[]) {
        // Инициализация переменной для хранения максимальной длины подпоследовательности
        int maxlength = 1;

        // Создание массива для хранения длин подпоследовательностей для каждого элемента
        int[] d = new int[numbers.length];

        // Инициализация всех элементов массива d значением 1
        for (int i = 0; i <= numbers.length-1; i++) {
            d[i] = 1;
        }

        // Перебор всех элементов входного массива
        for (int i = 0; i <= numbers.length-1; i++) {
            // Перебор всех элементов до текущего элемента
            for (int j = 0; j <= i - 1; j++) {
                // Проверка условия: если numbers[i] делится на numbers[j] без остатка и длина подпоследовательности через j больше, чем через i
                if (numbers[i] % numbers[j] == 0 && d[j]+1 > d[i]) {
                    // Обновление длины подпоследовательности через i
                    d[i] = d[j] + 1;
                    // Обновление максимальной длины подпоследовательности, если необходимо
                    if (d[i] > maxlength)
                        maxlength = d[i];
                }
            }
        }

        // Возврат максимальной длины подпоследовательности
        return maxlength;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

}