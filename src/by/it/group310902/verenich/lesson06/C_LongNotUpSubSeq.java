package by.it.group310902.verenich.lesson06;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

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

    int max(int x, int y){
        if(x>y) return x;
        else return y;
    }

    int Binary(int[] d, int n, int val){
        int l=0;
        int r= n-1;
        while(l<r){
            int mid = (l+r)/2;
            if(d[mid]>=val) l = mid+1;
            if (d[mid]< val) r= mid;
        }
        return l;
    }
    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
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
        int INF = (int) 1e9;
        int result = 0;
        int[] d = new int[n];
        int[] pos = new int[n];
        int[] prev = new int[n];
        d[0] = INF;
        pos[0] = -1;
        int length = 0;
        for(int i = 1; i<n;++i)
            d[i] = -INF;
        for(int i= 0;i<n; ++i){
            int j = Binary(d,n,m[i]);
            if(d[j-1]>= m[i] && m[i]> d[j]){
                d[j] = m[i];
                pos[j]= i;
                prev[i] = pos[j-1];
                length = max(length, j);
            }
        }
        for(int i = 0;i<n;++i){
            System.out.print(d[i] + " ");
        }
        Stack<Integer> st = new Stack<>();
        int p = pos[length];
        while (!st.empty()){
            System.out.print(st.pop() + " ");
        }
        result = length;
        System.out.println();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

}