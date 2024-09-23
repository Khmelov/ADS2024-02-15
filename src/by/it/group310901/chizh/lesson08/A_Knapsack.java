package by.it.group310901.chizh.lesson08;

import by.it.a_khmelev.lesson07.A_EditDist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: рюкзак с повторами

Первая строка входа содержит целые числа
    1<=W<=100000     вместимость рюкзака
    1<=n<=300        сколько есть вариантов золотых слитков
                     (каждый можно использовать множество раз).
Следующая строка содержит n целых чисел, задающих веса слитков:
  0<=w[1]<=100000 ,..., 0<=w[n]<=100000

Найдите методами динамического программирования
максимальный вес золота, который можно унести в рюкзаке.


Sample Input:
10 3
1 4 8
Sample Output:
10

Sample Input 2:

15 3
2 8 16
Sample Output 2:
14

*/

public class A_Knapsack {

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int w=scanner.nextInt();
        int n=scanner.nextInt();
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }
        int result = 0;

        int[][] dp = new int[n + 1][w + 1];
        for (int r = 1; r < n + 1; r++)
        {
            for (int c = 1; c < w + 1; c++)
            {
                if (gold[r - 1] > c)
                {
                    dp[r][c] = dp[r - 1][c];
                }
                else if (gold[r - 1] == c)
                {
                    dp[r][c] = 1;
                }
                else
                {
                    dp[r][c] = Math.max(dp[r - 1][c], Math.max(dp[r - 1][c - gold[r - 1]], dp[r][c - gold[r - 1]]));
                }
            }
        }
        for (int c = 1; c < w + 1; c++)
        {
            if (dp[n][c] == 1)
            {
                result = c;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
