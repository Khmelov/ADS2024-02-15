package by.it.group310901.baradzin.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

/**
 * Рассчитать число инверсий одномерного массива. Сложность алгоритма должна быть не хуже, чем O(n log n)
 *
 * <ol>
 *     <li>Первая строка содержит число 1<=n<=10000,</li>
 *     <li>вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.</li>
 * </ol>
 * <p>
 * Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].
 * <p>
 * Такая пара элементов называется инверсией массива. Количество инверсий в массиве является в некотором смысле его
 * мерой неупорядоченности: например, в упорядоченном по неубыванию массиве инверсий нет вообще, а в массиве,
 * упорядоченном по убыванию, инверсию образуют каждые (т.е. любые) два элемента.
 * <p>
 * Sample Input:<br/>
 * 5<br/>
 * 2 3 9 2 9
 * <p>
 * Sample Output:<br/>
 * 2
 * <p>
 * Головоломка (т.е. не обязательно). Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
 * Докажите рост производительности замерами времени. Большой тестовый массив можно прочитать свой или сгенерировать
 * его программно.
 */

public class C_GetInversions {

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataC.txt");
        var instance = new C_GetInversions();
//        var startTime = System.currentTimeMillis();
        var result = instance.calc(stream);
//        var finishTime = System.currentTimeMillis();
        System.out.print(result);
    }

    int calc(InputStream stream) throws FileNotFoundException {
        var scanner = new Scanner(stream);
        var a = new int[scanner.nextInt()];
        for (var i = 0; i < a.length; i++)
            a[i] = scanner.nextInt();
        return new InverseCounter(a, 0, a.length).invoke();
    }
}

class InverseCounter extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int left, right;
    private int inverses;

    InverseCounter(int[] arr, int left, int right) {
        this.arr = arr;
        this.left = left;
        this.right = right;
    }

    @Override
    protected Integer compute() {
        if (right - left < 2) return 0;
        var mid = (left + right) >>> 1;
        var left = new InverseCounter(arr, this.left, mid);
        var right = new InverseCounter(arr, mid, this.right);
        left.fork();
        right.fork();
        inverses += left.join();
        inverses += right.join();
        merge(arr, this.left, mid, this.right);
        return inverses;
    }

    void merge(int[] arr, int left, int mid, int right) {
        var range = Arrays.copyOfRange(arr, left, mid);
        for (var i = 0; i < range.length;)
            if ((range[i] <= arr[mid]) || mid >= right)
                arr[left++] = range[i++];
            else {
                arr[left++] = arr[mid++];
                inverses += range.length - i;
            }
        while (mid < right) arr[left++] = arr[mid++];
    }
}
