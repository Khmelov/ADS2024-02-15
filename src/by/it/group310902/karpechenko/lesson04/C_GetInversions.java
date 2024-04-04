package by.it.group310902.karpechenko.lesson04;

import java.io.FileInputStream;
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
    int Merge(int[] x, int l, int r){
        int ans = 0;
        int m = (l + r)/2;
        int lsize = m - l + 1;
        int rsize = r - m;

        int[] left = new int[lsize];
        int[] right = new int[rsize];

        for (int i = 0; i < lsize; i++)
            left[i] = x[i+l];
        for (int i = 0; i < rsize; i++)
            right[i] = x[i+m+1];

        int i = 0, j = 0, idx = l;
        while (i < lsize && j < rsize){
            if(left[i] <= right[j]){
                x[idx] = left[i];
                i++;
            }
            else{
                x[idx] = right[j];
                ans += lsize - i;
                j++;
            }
            idx++;
        }
        while (i < lsize){
            x[idx] = left[i];
            i++;
            idx++;
        }
        while (j < rsize){
            x[idx] = right[j];
            j++;
            idx++;
        }
        return ans;
    }
    int Inversions(int[] a, int l, int r){
        if (l >= r) return 0;
        int m = l + (r - l) / 2;
        int left = Inversions(a,l,m);
        int right = Inversions(a,m+1,r);
        return (left + right + Merge(a,l,r));
    }
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
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        result = Inversions(a,0,n-1);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
