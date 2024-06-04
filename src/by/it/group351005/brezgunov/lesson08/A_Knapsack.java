package by.it.group351005.brezgunov.lesson08;

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
        int weight = scanner.nextInt();
        int len = scanner.nextInt();
        int gold[] = new int[len];
        int max = 0;
        for (int i = 0; i < len; i++) {
            gold[i] = scanner.nextInt();
        }

        int[][] weightMatrix = new int[len][weight];
        weightMatrix[0][1] = (gold[0] == 1) ? 1 : 0;
        for (int i = 1; i < weight; i++) {
            if ((i + 1) % gold[0] == 0 && weightMatrix[0][i - 1] + gold[0] <= weight) {
                max += gold[0];
                weightMatrix[0][i] = max;
            } else {
                weightMatrix[0][i] = max;
            }
        }

        for (int i = 1; i < len; i++) {
            weightMatrix[i][0] = weightMatrix[i - 1][0];
            if (weightMatrix[i - 1][0] + gold[i] <= weightMatrix[0][0]) {
                weightMatrix[i][0] += gold[i];
            }
            for (int j = 1; j < weight; j++) {
                weightMatrix[i][j] = Math.max(weightMatrix[i - 1][j], weightMatrix[i][j - 1]);
                if (weightMatrix[i - 1][j] + gold[i] <= weightMatrix[0][j]) {
                    weightMatrix[i][j] += gold[i];
                }
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return weightMatrix[weightMatrix.length - 1][weightMatrix[0].length - 1];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
