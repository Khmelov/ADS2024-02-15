package by.it.group351003.sologub.lesson08;

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

    public static int maxGoldWeight(int W, int n, int[] gold) {
        int[][] maxW = new int[n+1][W+1];   //maxW[i][j] будет представлять максимальный вес золота,
                                            // который можно унести в рюкзаке, используя только первые i слитков и имея вместимость рюкзака j
                                            // n- amount; w - capacity
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                maxW[i][j] = maxW[i-1][j];
                if (gold[i-1] <= j) {
                    maxW[i][j] = Math.max(maxW[i][j], maxW[i][j-gold[i-1]] + gold[i-1]); //В этой формуле dp[i-1][j] соответствует случаю,
                    // когда текущий слиток не берется, а dp[i][j-w[i]] + w[i] соответствует случаю, когда текущий слиток берется в рюкзак.
                }
            }
        }

        return maxW[n][W];
    }

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int w=scanner.nextInt();
        int n=scanner.nextInt();
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }


        int result = maxGoldWeight(w, n, gold);
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
