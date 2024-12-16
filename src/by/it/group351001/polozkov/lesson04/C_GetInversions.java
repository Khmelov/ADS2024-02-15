package by.it.group351001.polozkov.lesson04;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_GetInversions.class.getResourceAsStream("dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
    private static int inverseCount;
    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        sort(a);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return inverseCount;
    }
    public static int[] sort(int[] mass) {
        inverseCount = 0;
        if (mass== null) return null;
        if (mass.length == 0) return mass;
        return sortirovka(mass,0,mass.length - 1);
    }

    static int[] sortirovka(int[] mass, int low, int high) {
        if (low > high - 1) return new int[] { mass[low] };
        int mid = (high + low) / 2;

        return merge(sortirovka(mass,low,mid), sortirovka(mass,mid + 1, high));
    }

    static int[] merge(int[] massl, int[] massr) {
        int curl = 0;
        int curr = 0;
        int[] mergedmass = new int[massl.length + massr.length];
        int mergedCur = 0;

        while (curl < massl.length && curr < massr.length) {

            if (massl[curl] <= massr[curr])
                mergedmass[mergedCur++] = massl[curl++];
            else {
                mergedmass[mergedCur++] = massr[curr++];
                inverseCount += (massl.length - curl);
            }
        }

        while (curl < massl.length)
            mergedmass[mergedCur++] = massl[curl++];

        while (curr < massr.length)
            mergedmass[mergedCur++] = massr[curr++];

        return mergedmass;
    }
}
