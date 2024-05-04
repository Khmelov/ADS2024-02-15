package by.it.group351001.tsiareshchanka.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_MergeSort {

    public int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();

        // Создание массива
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Сортировка слиянием
        mergeSort(a);

        return a;
    }

    private void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return; // Базовый случай: массив уже отсортирован или содержит 0 или 1 элемент
        }

        int[] temp = new int[array.length]; // Временный массив для объединения отсортированных половин
        mergeSort(array, temp, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] temp, int left, int right){
        if(left < right){
            int mid = (left + right) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid+1, right );
            merge(array, temp, left, mid, right);
        }
    }

    private void merge(int[] array, int[] temp, int left, int mid, int right) {
        // Копируем элементы во временный массив
        for (int i = left; i <= right; i++){
            temp[i] = array [i];
        }

        int i = left;
        int j = mid + 1;
        int k = 0;

        while ((i <= mid) && (j <= right)){
            if (temp[i] <= temp[j]){
                array[k++] = temp[i++];
            }
            else
                array[k++] = temp[j++];
        }
        while (i <= mid){
            array[k++] = temp [i++];
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result = instance.getMergeSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}