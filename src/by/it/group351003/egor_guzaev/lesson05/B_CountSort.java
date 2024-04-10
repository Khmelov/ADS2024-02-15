package by.it.group351003.egor_guzaev.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

    int[] countSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] points = new int[n];

        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }


        int[] count = new int[11];
        for (int i = 0; i < n; i++) {
            count[points[i]]++;
        }

        int[] sortedPoints = new int[n];
        int currentSortedIndex = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                sortedPoints[currentSortedIndex++] = i;
                count[i]--;
            }
        }

        return sortedPoints;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
