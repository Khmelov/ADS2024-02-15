package by.it.group310901.baradzin.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <dl>
 * <dt>Задача на программирование: рюкзак без повторами
 * <dt>Дано:
 * <dd><dl>
 * <dt>Первая строка ввода содержит целые числа:
 * <dd>1≤W≤100000 - вместимость рюкзака
 * <dd>1≤n≤300 - число золотых слитков (каждый можно использовать только один раз)
 * <dt>Вторая строка содержит n целых чисел:
 * <dd>0<=w[1]<=100000 ,..., 0<=w[n]<=100000 - веса слитков
 * <dt>Найдите методами динамического программирования максимальный вес золота, который можно унести в рюкзаке.
 * </dl></dd>
 * <dt>Sample Input:
 * <dd>10 3<br/>1 4 8
 * <dt>Sample Output:
 * <dd>9
 * </dl>
 */

public class B_Knapsack {
    int getMaxWeight(InputStream stream) {
        var scanner = new Scanner(stream);
        var w = scanner.nextInt();
        var weights = new int[scanner.nextInt()];
        for (var i = 0; i < weights.length; i++)
            weights[i] = scanner.nextInt();
        return knapsackWithoutRepsBU(w, weights);
    }

    int knapsackWithoutRepsBU(int W, int[] weights) {
        var n = weights.length;
        var D = new int[W + 1][n + 1];
        for (var i = 1; i <= n; i++)
            for (var w = 0; w <= W; w++) {
                D[w][i] = D[w][i - 1];
                if (weights[i - 1] <= w) D[w][i] = Math.max(D[w][i], D[w - weights[i - 1]][i - 1] + weights[i - 1]);
            }
        return D[W][n];
    }

    public static void main(String[] args) throws FileNotFoundException {
        var stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        var instance = new B_Knapsack();
        var res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
