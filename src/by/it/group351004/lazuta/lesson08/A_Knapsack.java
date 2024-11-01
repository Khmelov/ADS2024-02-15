package by.it.group351004.lazuta.lesson08;

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
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество вариантов золотых слитков
        int[] weights = new int[n]; // Веса слитков золота

        // Заполнение массива весов слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Создание массива для хранения максимального веса золота в рюкзаке
        int[] maxWeights = new int[W + 1];

        // Вычисление максимального веса золота в рюкзаке
        for (int w = 1; w <= W; w++) {
            for (int i = 0; i < n; i++) {
                if (weights[i] <= w) {
                    int weightWithItem = maxWeights[w - weights[i]] + weights[i];
                    if (weightWithItem > maxWeights[w]) {
                        maxWeights[w] = weightWithItem;
                    }
                }
            }
        }

        // Возвращение максимального веса золота в рюкзаке
        return maxWeights[W];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
