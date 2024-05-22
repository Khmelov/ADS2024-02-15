package by.it.group310901.baradzin.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Задача на программирование: наибольшая невозрастающая подпоследовательность</p>
 * <p>Дано:</p>
 * <ul>
 * <li>целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )</li>
 * <li>массив A[1…n] натуральных чисел, не превосходящих 2E9.</li>
 * </ul>
 * <p>Необходимо:</p>
 * <ul>
 * <li>Выведите максимальное 1<=k<=n, для которого гарантированно найдётся подпоследовательность индексов
 * i[1]<i[2]<…<i[k] <= длины k, для которой каждый элемент A[i[k]] не больше любого предыдущего т.е. для всех 1<=j<k,
 * A[i[j]]>=A[i[j+1]].</li>
 * <li>В первой строке выведите её длину k, во второй - её индексы i[1]<i[2]<…<i[k] соблюдая A[i[1]]>=A[i[2]]>= ...
 * >=A[i[n]].</li>
 * <li>(индекс начинается с 1)</li>
 * </ul>
 * <p>Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ</p>
 * <p>
 * Sample Input:<br/>
 * 5<br/>
 * 5 3 4 4 2<br/>
 * Sample Output:<br/>
 * 4<br/>
 * 1 3 4 5<br/>
 * </p>
 */

public class C_LongNotUpSubSeq {
    int getNotUpSeqSize(InputStream stream) {
        var scanner = new Scanner(stream);
        var m = new Integer[scanner.nextInt()];
        for (var i = 0; i < m.length; i++)
            m[i] = scanner.nextInt();
        return new SeqCheck((arr, current, next) -> arr[current] >= arr[next]).getLength(m);
    }

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        var instance = new C_LongNotUpSubSeq();
        var result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }
}
