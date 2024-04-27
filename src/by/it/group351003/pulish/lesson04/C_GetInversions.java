package by.it.group351003.pulish.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Вычисление числа инверсий
        int result = mergeSortAndCountInversions(a, 0, n - 1);

        return result;
    }

    private int mergeSortAndCountInversions(int[] array, int left, int right) {
        int countInversions = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Рекурсивно сортируем две половины массива и вычисляем число инверсий
            countInversions += mergeSortAndCountInversions(array, left, mid);
            countInversions += mergeSortAndCountInversions(array, mid + 1, right);

            // Слияние отсортированных половин и вычисление числа инверсий
            countInversions += mergeAndCountInversions(array, left, mid, right);
        }
        return countInversions;
    }

    private int mergeAndCountInversions(int[] array, int left, int mid, int right) {
        // Размеры временных массивов
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Создание временных массивов
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Копирование элементов во временные массивы
        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[mid + 1 + j];
        }

        // Слияние временных массивов обратно в основной массив и вычисление числа инверсий
        int i = 0;  // Индекс для временного массива L
        int j = 0;  // Индекс для временного массива R
        int k = left;  // Индекс для основного массива
        int countInversions = 0;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
                countInversions += n1 - i; // Все элементы, начиная с L[i], образуют инверсии с R[j]
            }
            k++;
        }

        // Копирование оставшихся элементов из временного массива L
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        // Копирование оставшихся элементов из временного массива R
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }

        return countInversions;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}