package by.it.group310902.Morov.lesson04;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_BinaryFind {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_BinaryFind.class.getResourceAsStream("dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        int[] result = instance.findIndex(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

    int binarySearch(int[] array, int key, int low, int high) {
        int middle = low + (high - low) / 2;
        if (high < low) {
            return -1;
        }
        if (key == array[middle]) {
            return middle + 1; // Возвращаем индекс с учетом 1-базированной индексации
        } else if (key < array[middle]) {
            return binarySearch(array, key, low, middle - 1);
        } else {
            return binarySearch(array, key, middle + 1, high);
        }
    }

    int[] findIndex(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int k = scanner.nextInt();
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            int value = scanner.nextInt();
            result[i] = binarySearch(a, value, 0, n - 1);
        }

        return result;
    }
}