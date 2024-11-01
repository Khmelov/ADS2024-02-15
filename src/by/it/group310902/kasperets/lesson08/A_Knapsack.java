package by.it.group310902.kasperets.lesson08;

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
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }
        int[] dp = new int[W + 1];
        dp[0] = 0; // Инициализируем dp[0] = 0
        // Цикл по всем возможным вместимостям рюкзака от 1 до W
        for (int i = 1; i <= W; i++) {
            // Для каждой вместимости перебираем все слитки
            for (int j = 0; j < n; j++) {
                // Если вес текущего слитка меньше или равен текущей вместимости
                if (weights[j] <= i) {
                    // Сравниваем текущее значение dp[i] с суммой веса текущего слитка
                    // и максимального веса, который можно унести в рюкзаке вместимостью i - weights[j]
                    dp[i] = Math.max(dp[i], dp[i - weights[j]] + weights[j]);
                }
            }
        }

        // Возвращаем максимальный вес золота, который можно унести в рюкзаке вместимостью W
        return dp[W];
    }
    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
