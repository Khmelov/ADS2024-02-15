package by.it.group310901.baradzin.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <p>В первой строке источника данных даны:</p>
 * <ol>
 * <li>целое число 1<=n<=100000 (размер массива)</li>
 * <li>сам массив A[1...n] из n различных натуральных чисел, не превышающих 10E9, в порядке возрастания.</li>
 * </ol>
 * <p>Во второй строке:</p>
 * <ol>
 * <li>целое число 1<=k<=10000 (сколько чисел нужно найти)</li>
 * <li>k натуральных чисел b_1,..., b_k не превышающих 10E9 (сами числа)</li>
 * </ol>
 * <p>Для каждого i от 1 до k необходимо вывести индекс 1<=j<=n, для которого A[j]=b_i, или -1, если такого j нет.</p>
 * <p>
 * Sample Input:<br/>
 * 5 1 5 8 12 13<br/>
 * 5 8 1 23 1 11<br/>
 * Sample Output:<br/>
 * 3 1 -1 1 -1<br/>
 * <p>(!) Обратите внимание на смещение начала индекса массивов JAVA относительно условий задачи</p>
 */

public class A_BinaryFind {
    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataA.txt");
        var instance = new A_BinaryFind();
//        var startTime = System.currentTimeMillis();
        var result = instance.findIndex(stream);
//        var finishTime = System.currentTimeMillis();
        for (var index : result) {
            System.out.print(index + " ");
        }
    }

    int[] findIndex(InputStream stream) {
        var scanner = new Scanner(stream);
        var a = new int[scanner.nextInt()];
        for (var i = 0; i < a.length; i++)
            a[i] = scanner.nextInt();
        var result = new int[scanner.nextInt()];
        for (var i = 0; i < result.length; i++) result[i] = find(a, scanner.nextInt());
        return result;
    }

    int find(int[] a, int b) {
        return find(a, b, 0, a.length - 1);
    }

    int find(int[] a, int b, int left, int right) {
        return find(a, b, left, (left + right) >>> 1, right);
    }

    int find(int[] a, int b, int left, int mid, int right) {
        return (left > right)
                ? -1
                : (a[mid] < b)
                ? find(a, b, mid + 1, right)
                : (b < a[mid])
                ? find(a, b, left, mid - 1)
                : mid + 1;
    }
}
