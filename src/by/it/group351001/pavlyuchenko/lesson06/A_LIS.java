package by.it.group351001.pavlyuchenko.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    где каждый элемент A[i[k]] больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]<A[i[j+1]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    1 3 3 2 6

    Sample Output:
    3
*/

public class A_LIS {


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_LIS.class.getResourceAsStream("dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }

    private int BinarySearch(int[] arr,int search) {
    int result, low, high,mid;
    boolean stop;
    high = (arr.length - 1) ;
    low = 0;
    mid = (high + low) >> 1;
    while (low <= high) {
        mid = (high + low) >> 1;
      if (arr[mid] == search) {
        break;
      } else {
          if (arr[mid] < search) {
            low = mid + 1;
          } else {
              high = mid - 1;
          }
      }
        }
      if (search > arr[mid]) {
        result = mid + 1;
      } else{
          result = mid;
      }

     return result;
    }

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int index;
        int n = scanner.nextInt();
        int[] m = new int[n];
        int[] d = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        int result = 0;
        for (int i = 0; i < n; i++ ) {
          d[i] = Integer.MAX_VALUE;
        }
        d[0] = m[0];
        for (int i = 1; i < n; i++) {
          index = BinarySearch(d,m[i]);
          if (((index == 0) && (d[1] == Integer.MAX_VALUE) && (m[i] < d[index])) || ((index != 0) && (d[index-1] < m[i]) && (m[i] < d[index]))) {
            d[index] = m[i];
            result = Integer.max(result,index+1);
          }
        }



        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
}
