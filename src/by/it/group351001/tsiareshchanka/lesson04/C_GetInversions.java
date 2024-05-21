package by.it.group351001.tsiareshchanka.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {
    private int result = 1; // instance variable for storing the result

    // Method to calculate the number of inversions
    int calc(InputStream stream) throws FileNotFoundException {
        // Preparation for reading data
        Scanner scanner = new Scanner(stream);

        // Read the size of the array
        int n = scanner.nextInt();

        // Read the array elements
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Perform merge sort
        mergeSort(a);

        // Return the result
        return result;
    }

    // Merge sort implementation
    private void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return; // Base case: the array is already sorted or contains 0 or 1 element
        }

        int[] temp = new int[array.length]; // Temporary array for merging sorted halves
        mergeSort(array, temp, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] temp, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    private void merge(int[] array, int[] temp, int left, int mid, int right) {
        // Copy elements to the temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }

        int i = left; // Index for the left half
        int j = mid + 1; // Index for the right half
        int k = left; // Index for the resulting merged array

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                //if (array[k] < temp[i]){
                //  result++;
                //}
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
                result++;
            }
        }

        // Add the remaining elements from the left half (if any)
        while (i <= mid) {
            array[k++] = temp[i++];
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group351001/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}