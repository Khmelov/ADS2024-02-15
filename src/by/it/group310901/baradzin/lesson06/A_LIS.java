package by.it.group310901.baradzin.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>Задача на программирование: наибольшая возрастающая подпоследовательность</p>
 * <p>см.<a href="https://ru.wikipedia.org/wiki/Задача_поиска_наибольшей_увеличивающейся_подпоследовательности">
 * https://ru.wikipedia.org/wiki/Задача_поиска_наибольшей_увеличивающейся_подпоследовательности</a><br/>
 * <a href="https://en.wikipedia.org/wiki/Longest_increasing_subsequence">
 * https://en.wikipedia.org/wiki/Longest_increasing_subsequence</a></p>
 * <p>Дано:</p>
 * <ul>
 * <li>целое число 1≤n≤1000</li>
 * <li>массив A[1…n] натуральных чисел, не превосходящих 2E9.</li>
 * </ul>
 * <p>Необходимо: Выведите максимальное 1<=k<=n, для которого гарантированно найдётся подпоследовательность индексов
 * i[1]<i[2]<…<i[k] <= длины k, для которой каждый элемент A[i[k]]больше любого предыдущего т.е. для всех 1<=j<k,
 * A[i[j]]<A[i[j+1]].</p>
 * <p>Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ</p>
 * <p>
 * Sample Input:<br/>
 * 5<br/>
 * 1 3 3 2 6<br/>
 * Sample Output:<br/>
 * 3<br/>
 * </p>
 */

public class A_LIS {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson06/dataA.txt");
        var instance = new A_LIS();
        var result = instance.getSeqSize(stream);
        System.out.print(result);
    }

    int getSeqSize(InputStream stream) {
        var scanner = new Scanner(stream);
        var m = new int[scanner.nextInt()];
        for (var i = 0; i < m.length; i++)
            m[i] = scanner.nextInt();
        return getLength(m);
    }

    int getLength(int[] arr) {
        return getLength(arr, -1, 0);
    }

    int getLength(int[] arr, int current, int next) {
        if (next == arr.length) return 0;
        var includes = 0;
        if (current == -1 || arr[current] < arr[next])
            includes = 1 + getLength(arr, next, next + 1);
        var excludes = getLength(arr, current, next + 1);
        return Math.max(includes, excludes);
    }
}
