package by.it.group310902.strizhevskiy.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием


        int[] temp = new int[n];
        
        int[] hooks = new int[32]; //14 - better
        int lastHook = 1;
        hooks[0] = 0;
        hooks[1] = 1;
        hooks[2] = 2;
        
        boolean lastMerge = false;
        while(lastHook != 0){
            
            while((lastHook > 0) && (hooks[lastHook]-hooks[lastHook-1] == hooks[lastHook+1]-hooks[lastHook] || lastMerge)){

                // начало слияния
                int res = hooks[lastHook-1];
                int arr2 = hooks[lastHook];
                int size1 = hooks[lastHook]-hooks[lastHook-1];
                int aEnd2 = hooks[lastHook+1];

                System.arraycopy(a,res,temp,0,size1);
                int tmp = 0;

                while((tmp < size1) && (arr2 < aEnd2)){
                    if(temp[tmp] <= a[arr2]){
                        a[res] = temp[tmp];
                        tmp++;
                    } else {
                        a[res] = a[arr2];
                        arr2++;
                    }
                    res++;
                }

                while(tmp < size1){
                    a[res] = temp[tmp];
                    tmp++;
                    res++;
                }

                while(arr2 < aEnd2){
                    a[res] = a[arr2];
                    arr2++;
                    res++;
                }
                // конец слияния

                hooks[lastHook] = hooks[lastHook+1];
                lastHook--;
            }
            
            if(hooks[lastHook+1] < a.length){
                lastHook++;
                hooks[lastHook+1] = hooks[lastHook]+1;
                continue;
            }
            lastMerge = true;
        }




        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
