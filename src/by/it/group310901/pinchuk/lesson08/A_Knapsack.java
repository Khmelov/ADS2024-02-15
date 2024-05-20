package by.it.group310901.pinchuk.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        // Считывание входных данных
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // вместимость рюкзака
        int n = scanner.nextInt(); // количество типов золотых слитков
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Массив динамического программирования для хранения максимального веса для каждой вместимости
        int[] dp = new int[W + 1];

        // Заполнение массива dp
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (gold[j] <= i) {
                    dp[i] = Math.max(dp[i], dp[i - gold[j]] + gold[j]);
                }
            }
        }

        // Максимальный вес для полной вместимости W будет в dp[W]
        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
