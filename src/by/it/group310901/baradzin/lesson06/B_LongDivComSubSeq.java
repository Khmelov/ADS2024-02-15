package by.it.group310901.baradzin.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Задача на программирование: наибольшая кратная подпоследовательность</p>
 * <p>Дано:</p>
 * <ul>
 * <li>целое число 1≤n≤1000</li>
 * <li>массив A[1…n] натуральных чисел, не превосходящих 2E9.</li>
 * </ul>
 * <p>Необходимо: Выведите максимальное 1<=k<=n, для которого гарантированно найдётся подпоследовательность индексов
 * i[1]<i[2]<…<i[k] <= длины k, для которой каждый элемент A[i[k]] делится на предыдущий т.е. для всех 1<=j<k,
 * A[i[j+1]] делится на A[i[j]].</p>
 * <p>Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ</p>
 * <p>
 * Sample Input:<br/>
 * 4<br/>
 * 3 6 7 12<br/>
 * Sample Output:<br/>
 * 3<br/>
 * </p>
 */

public class B_LongDivComSubSeq {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson06/dataB.txt");
        var instance = new B_LongDivComSubSeq();
        var result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

    int getDivSeqSize(InputStream stream) {
        var scanner = new Scanner(stream);
        var m = new Integer[scanner.nextInt()];
        for (var i = 0; i < m.length; i++)
            m[i] = scanner.nextInt();
        return new SeqCheck((arr, current, next) -> arr[next] % arr[current] == 0).getLength(m);
    }
}
