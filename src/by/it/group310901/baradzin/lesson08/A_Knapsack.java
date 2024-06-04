package by.it.group310901.baradzin.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <dl>
 * <dt>Задача на программирование: рюкзак с повторами
 * <dt>Дано:
 * <dd><dl>
 * <dt>Первая строка ввода содержит целые числа:
 * <dd>1≤W≤100000 - вместимость рюкзака
 * <dd>1≤n≤300 - количество различных типов золотых слитков (каждый может быть использован неограниченное число раз)
 * <dt>Вторая строка содержит n целых чисел:
 * <dd>0≤w[1],...,w[n]≤100000 - веса слитков
 * <dt>Найдите методами динамического программирования максимальный вес золота, который можно унести в рюкзаке.
 * </dl></dd>
 * <dt>Sample Input:
 * <dd>10 3<br/>1 4 8</dd>
 * <dt>Sample Output:
 * <dd>10
 * <dt>Sample Input 2:
 * <dd>15 3<br/>2 8 16
 * <dt>Sample Output 2:
 * <dd>14
 * </dl>
 */

public class A_Knapsack {
    int getMaxWeight(InputStream stream) {
        var scanner = new Scanner(stream);
        var w = scanner.nextInt();
        var weights = new int[scanner.nextInt()];
        for (var i = 0; i < weights.length; i++)
            weights[i] = scanner.nextInt();
        return knapsackWithRepsBU(w, weights);
    }

    int knapsackWithRepsBU(int W, int[] weights) {
        var D = new int[W + 1];
        for (var w = 1; w <= W; w++)
            for (var weight : weights)
                if (weight <= w)
                    D[w] = Math.max(D[w], D[w - weight] + weight);
        return D[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        var stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        var instance = new A_Knapsack();
        var res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
