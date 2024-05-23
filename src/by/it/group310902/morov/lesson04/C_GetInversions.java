package by.it.group310902.Morov.lesson04;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_GetInversions.class.getResourceAsStream("dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }

    int merge(int[] a, int[] left, int[] right) {
        int inversions = 0;
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
                inversions += left.length - i;
            }
        }
        while (i < left.length) {
            a[k++] = left[i++];
        }
        while (j < right.length) {
            a[k++] = right[j++];
        }
        return inversions;
    }

    int mergeSort(int[] a) {
        if (a.length <= 1) {
            return 0;
        }
        int mid = a.length / 2;
        int[] left = new int[mid];
        int[] right = new int[a.length - mid];
        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, a.length - mid);
        int inversions = mergeSort(left) + mergeSort(right);
        inversions += merge(a, left, right);
        return inversions;
    }

    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = mergeSort(a);
        return result;
    }
}