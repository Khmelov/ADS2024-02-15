package by.it.group351003.kalinckovich.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Задача на программирование: наибольшая кратная подпоследовательность

Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] делится на предыдущий
    т.е. для всех 1<=j<k, A[i[j+1]] делится на A[i[j]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    4
    3 6 7 12

    Sample Output:
    3
*/

public class B_LongDivComSubSeq {


    int getDivSeqSize(InputStream stream) throws FileNotFoundException {
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
        //тут реализуйте логику задачи методами динамического программирования (!!!)
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
                if (m[i] % m[fArr[mid]] == 0) {
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

        int result = s.length + 1;


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

}