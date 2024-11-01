package by.it.group351001.polozkov.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;
/*
Задача на программирование: рюкзак без повторов

Первая строка входа содержит целые числа
    1<=W<=100000     вместимость рюкзака
    1<=n<=300        число золотых слитков
                    (каждый можно использовать только один раз).
Следующая строка содержит n целых чисел, задающих веса каждого из слитков:
  0<=w[1]<=100000 ,..., 0<=w[n]<=100000

Найдите методами динамического программирования
максимальный вес золота, который можно унести в рюкзаке.

Sample Input:
10 3
1 4 8
Sample Output:
9

*/

public class B_Knapsack {

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int w=scanner.nextInt();
        int n=scanner.nextInt();
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }

        boolean[] temp = new boolean[w+1];
        Arrays.fill(temp,false);
        temp[0] = true;
        for (int i = 0; i < n; i++)
            for (int x = w - gold[i]; x >= 0; x--)
                temp[x + gold[i]] |= temp[x];
        for(int i=w;true;i--)
            if(temp[i])
                return i;


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
