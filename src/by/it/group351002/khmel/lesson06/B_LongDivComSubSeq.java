package by.it.group351002.khmel.lesson06;

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
/*найти длину самой длинной делимой подпоследовательности */
/* найти максимальную длину k(1 ≤ k≤ n) такую,
 что существует подпоследовательность индексов
 i[1] < i[2] < ... < i[k] ≤ n, где каждый элемент m[i[j+1]]делится на
 предыдущий элемент m[i[j]].*/
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
        int result = 0;

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[] seq = new int[n];
/* элемент по индексу i делится на элемент по индексу j.  и превышает ли длина делимой подпоследовательности,
заканчивающуюся индексом j плюс 1 (путем включения элемента m[i]), текущую длину делимой
подпоследовательности, заканчивающейся индексом i.*/

        for (int i = 0; i < n; i++) {
            seq[i] = 1;
            for (int j = 0; j < i; j++) {
                if (m[i] % m[j] == 0 && seq[j] + 1 > seq[i]) {
                    seq[i] = seq[j] + 1;
                }
            }
            result = Math.max(result, seq[i]);
        }

        return result;
    }
/* Если оба условия соблюдены=> делимая подпоследовательность, заканчивающаяся индексом,
j может быть расширена за счет включения элемента m[i]. В этом случае seq[i]обновляется до seq[j] + 1*/

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

}