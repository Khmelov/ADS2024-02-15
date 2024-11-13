package by.it.group351001.yakovenko.lesson08;

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

    int getMaxWeight(InputStream stream) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество видов слитков
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt(); // Веса слитков
        }

        int[][] dp = new int[n + 1][W + 1]; // Таблица динамического программирования

        // Заполнение таблицы
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                dp[i][j] = dp[i - 1][j]; // Не берем текущий предмет
                for (int k = 1; k * gold[i - 1] <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * gold[i - 1]] + k * gold[i - 1]);
                    // Берем k экземпляров текущего предмета
                }
            }
        }

        int result = dp[n][W];
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