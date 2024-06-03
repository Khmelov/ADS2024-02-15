package by.it.group351004.lazuta.lesson08;

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
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество золотых слитков
        int[] weights = new int[n]; // Веса каждого из слитков золота

        // Заполнение массива весов слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Создание массива для хранения максимального веса золота в рюкзаке
        int[][] maxWeights = new int[n + 1][W + 1];

        // Вычисление максимального веса золота в рюкзаке
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (weights[i - 1] <= w) {
                    int weightWithItem = maxWeights[i - 1][w - weights[i - 1]] + weights[i - 1];
                    maxWeights[i][w] = Math.max(weightWithItem, maxWeights[i - 1][w]);
                } else {
                    maxWeights[i][w] = maxWeights[i - 1][w];
                }
            }
        }

        // Возвращение максимального веса золота в рюкзаке
        return maxWeights[n][W];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}