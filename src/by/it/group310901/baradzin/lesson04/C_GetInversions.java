package by.it.group310901.baradzin.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

interface InverseCounter {
    Integer invoke();
}

/**
 * <p>Рассчитать число инверсий одномерного массива. Сложность алгоритма должна быть не хуже, чем O(n log n)</p>
 * <ol>
 * <li>Первая строка содержит число 1<=n<=10000,</li>
 * <li>вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.</li>
 * </ol>
 * <p>Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].</p>
 * <p>Такая пара элементов называется инверсией массива. Количество инверсий в массиве является в некотором смысле
 * его мерой неупорядоченности: например, в упорядоченном по неубыванию массиве инверсий нет вообще, а в массиве,
 * упорядоченном по убыванию, инверсию образуют каждые (т.е. любые) два элемента.</p>
 * <p>
 * Sample Input:<br/>
 * 5<br/>
 * 2 3 9 2 9<br/>
 * Sample Output:<br/>
 * 2<br/>
 * </p>
 * <p>Головоломка (т.е. не обязательно). Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
 * Докажите рост производительности замерами времени. Большой тестовый массив можно прочитать свой или сгенерировать
 * его программно.</p>
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

    int calc(InputStream stream) {
        var scanner = new Scanner(stream);
        var a = new int[scanner.nextInt()];
        for (var i = 0; i < a.length; i++)
            a[i] = scanner.nextInt();
        return new AsyncInverseCounter(a).invoke();
    }
}

class AsyncInverseCounter extends RecursiveTask<Integer> implements InverseCounter {
    private final int[] arr;
    private final int right;
    private int left, mid, inverses;

    AsyncInverseCounter(int[] arr) {
        this(arr, 0, arr.length);
    }

    AsyncInverseCounter(int[] arr, int left, int right) {
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.mid = (left + right) >>> 1;
    }

    @Override
    protected Integer compute() {
        if (right - left < 2) return 0;
        var leftCounter = new AsyncInverseCounter(arr, left, mid);
        var rightCounter = new AsyncInverseCounter(arr, mid, right);
        leftCounter.fork();
        rightCounter.fork();
        inverses += leftCounter.join();
        inverses += rightCounter.join();
        merge();
        return inverses;
    }

    private void merge() {
        var range = Arrays.copyOfRange(arr, left, mid);
        var i = 0;
        while (i < range.length && mid < right) if (range[i] <= arr[mid]) arr[left++] = range[i++];
        else {
            arr[left++] = arr[mid++];
            inverses += range.length - i;
        }
        while (i < range.length) arr[left++] = range[i++];
        while (mid < right) arr[left++] = arr[mid++];
    }
}

class SyncInverseCounter implements InverseCounter {
    private final int[] arr;
    private final int right;
    private int left, mid, inverses;

    SyncInverseCounter(int[] arr) {
        this(arr, 0, arr.length);
    }

    SyncInverseCounter(int[] arr, int left, int right) {
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.mid = (left + right) >>> 1;
    }

    public Integer invoke() {
        if (right - left < 2) return 0;
        inverses += new SyncInverseCounter(arr, left, mid).invoke();
        inverses += new SyncInverseCounter(arr, mid, right).invoke();
        merge();
        return inverses;
    }

    private void merge() {
        var range = Arrays.copyOfRange(arr, left, mid);
        var i = 0;
        while (i < range.length && mid < right) if (range[i] <= arr[mid]) arr[left++] = range[i++];
        else {
            arr[left++] = arr[mid++];
            inverses += range.length - i;
        }
        while (i < range.length) arr[left++] = range[i++];
        while (mid < right) arr[left++] = arr[mid++];
    }
}
