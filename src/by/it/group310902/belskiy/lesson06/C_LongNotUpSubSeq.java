package by.it.group310902.belskiy.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
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

    int getNotUpSeqSize(InputStream stream) {
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
        int result = 0;

        if (n < 1) return 0;

        int[] a = new int[n];

        a[0] = 1;

        for (int i = 1; i < n; i++) {
            a[i] = 1;

            for (int j = 0; j < i; j++) {
                if (m[i] <= m[j]) {
                    a[i] = Math.max(a[i], a[j] + 1);
                }
            }

            result = Math.max(result, a[i]);
        }


        int index = -1;
        for(int i = 0; i < n; i++){
            if(a[i] == result){
                index = i;
                break;
            }
        }

        //5 3 4 4 2
        //1 2 2 3 4

        System.out.println("Индексы результата: ");

        ArrayList<Integer> r = new ArrayList<>();
        r.add(index);
        int tmp_a = result;
        int tmp_m = m[index];
        for(int i = index - 1; i >= 0; i--){
            if(a[i] == tmp_a - 1 && m[i] >= tmp_m){
                //System.out.print(i + " ");
                r.add(i);
                tmp_a = a[i];
                tmp_m = m[i];
            }
        }

        for (int i = r.size() - 1; i >= 0; i--) {
            System.out.print(r.get(i) + 1 + " ");
        }
        System.out.println();


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        //InputStream stream = new FileInputStream(root + "by/it/group310902/belskiy/lesson06/dataC.txt");

        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}