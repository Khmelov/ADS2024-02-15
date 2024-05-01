package by.it.group310901.eremeiko.lesson04;

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

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        // Создаем и заполняем массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!

        // Вызываем функцию сортировки и подсчета инверсий
        result = sort(a, 0, a.length - 1);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // Возвращаем количество инверсий
        return result;
    }

    int sort(int[] arr, int left, int right) {
        // Инициализируем счетчик инверсий
        int count = 0;
        // Находим середину массива
        int m = (left + right) / 2;
        // Если в массиве больше одного элемента, разделяем его и сортируем каждую половину
        if (right - left > 1) {
            count += sort(arr, left, m - 1);
            count
                    += sort(arr, m, right);
        }
        // Сливаем отсортированные половины и подсчитываем количество инверсий
        count += merge(arr, left, m, right);
        // Возвращаем количество инверсий
        return count;
    }

    int merge(int[] arr, int left, int middle, int right) {
        // Инициализируем указатели и счетчик инверсий
        int a = 0, b = 0, count = 0;
        // Создаем массив для хранения результата
        int[] result = new int[right - left + 1];
        // Пока в обоих половинах есть элементы...
        while ((left + a < middle) && (middle + b <= right)) {
            // Если элемент из левой половины меньше или равен элементу из правой половины...
            if (arr[left + a] <= arr[middle + b]) {
                // ...добавляем его в результирующий массив и сдвигаем указатель в левой половине
                result[a + b] = arr[left + a];
                a++;
            } else {
                // ...иначе добавляем элемент из правой половины и сдвигаем указатель в правой половине
                result[a + b] = arr[middle + b];
                b++;
                // Каждый раз, когда мы добавляем элемент из правой половины, увеличиваем счетчик инверсий
                count++;
            }
        }
        // Если в левой половине остались элементы, добавляем их все в результирующий массив
        // и увеличиваем счетчик инверсий на количество оставшихся элементов
        while (left + a < middle) {
            result[a + b] = arr[left + a];
            a++;
            count++;
        }

        // Если в правой половине остались элементы, добавляем их все в результирующий массив
        while (middle + b <= right) {
            result[a + b] = arr[middle + b];
            b++;
        }
        // Копируем отсортированный массив обратно в исходный массив
        if (a + b >= 0)
            System.arraycopy(result, 0, arr, left, a + b);
        // Возвращаем количество инверсий
        return count;
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
