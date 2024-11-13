package by.it.group310902.pashkovich.lesson08;

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
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество доступных слитков
        int[] weights = new int[n]; // Массив весов слитков

        // Заполнение массива весов слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Создание массива для хранения максимального веса золота для каждой вместимости рюкзака
        int[] dp = new int[W + 1];

        // Заполнение массива dp
        for (int i = 1; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (weights[j] <= i) {
                    int val = dp[i - weights[j]] + weights[j];
                    if (val > dp[i]) {
                        dp[i] = val;
                    }
                }
            }
        }

        return dp[W];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
