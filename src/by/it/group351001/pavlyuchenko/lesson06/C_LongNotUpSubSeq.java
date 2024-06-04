package by.it.group351001.pavlyuchenko.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_LongDivComSubSeq.class.getResourceAsStream("dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
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
                if (arr[mid] > search) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        if ((search > arr[mid])) {
            result = mid;
        } else{
            result = mid + 1;
        }

        return result;
    }

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        int[] d = new int[n];
        ArrayList<Integer> indexes;
        indexes = new ArrayList<>();
        int index;
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++ ) {
            d[i] = Integer.MIN_VALUE;
        }
        d[0] = m[0];
        indexes.add(1);
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int result = 0;
        for (int i = 1; i < n; i++) {
            index = BinarySearch(d,m[i]);
            if (((index == 0) && (d[1] == Integer.MIN_VALUE) && (m[i] > d[index])) || ((index != 0) && (d[index-1] >= m[i]) && (m[i] >= d[index]))) {
                d[index] = m[i];
                if (index <= indexes.size()-1) {
                    indexes.remove(index);
                }
                indexes.add(index,i + 1);

                result = Integer.max(result,index+1);
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

}