package by.it.group310902.kasperets.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

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

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Capacity of the knapsack
        int n = scanner.nextInt(); // Number of gold bars
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt(); // Weights of the gold bars
        }

        // DP table where dp[i][j] represents the maximum weight
        // of gold that can fit into a knapsack of capacity j using
        // the first i items
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0; // Base case initialization
                } else if (gold[i - 1] <= j) {
                    // Item can be included
                    dp[i][j] = Math.max(gold[i - 1] + dp[i - 1][j - gold[i - 1]], dp[i - 1][j]);
                } else {
                    // Item cannot be included
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // The answer is in dp[n][W], the bottom-right corner of the table
        return dp[n][W];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
