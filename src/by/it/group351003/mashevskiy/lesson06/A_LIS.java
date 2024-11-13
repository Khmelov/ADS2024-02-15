package by.it.group351003.mashevskiy.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Задача на программирование: наибольшая возрастающая подпоследовательность
см.     https://ru.wikipedia.org/wiki/Задача_поиска_наибольшей_увеличивающейся_подпоследовательности
        https://en.wikipedia.org/wiki/Longest_increasing_subsequence

Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]]больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]<A[i[j+1]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    1 3 3 2 6

    Sample Output:
    3
*/

public class A_LIS {


    int getSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        int[] fArr = new int[n + 1];
        fArr[0] = 0;
        for(int i = 1;i < n + 1;i++){
            fArr[i] = m[i - 1];
        }
        int[] sArr = Arrays.copyOf(m,n);
        int l;
        l = 0;
        int lI;
        int hI;
        int mid;
        int newL;
        for(int i = 0;i < n - 1; i++){
            lI = 1;
            hI = l + 1;
            while(lI < hI) {
                mid = lI + (hI - lI) / 2;
                if (m[fArr[mid]] < m[i]) {
                    lI = mid + 1;
                } else {
                    hI = mid - 1;
                }
            }
            newL = lI;
            sArr[i] = fArr[newL - 1];
            fArr[newL] = i;
            if(newL > l){
                l = newL;
            }
        }
        int[] s = new int[l];
        int k = fArr[l];
        for(int i = l - 1;i > 0;i--){
            s[i] = m[k];
            k = sArr[k];
        }

        int result = s[s.length - 1];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
