package by.it.group351005.brezgunov.lesson08;

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
        for (int i = gold[0] - 1; weight - i >= gold[0]; i++) {
            weightMatrix[0][i] = gold[0];
        }

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < weight; j++) {
                weightMatrix[i][j] = (gold[i] <= j + 1) ? gold[i] : weightMatrix[i - 1][j];
                int k;
                k = i - 1;
                while (k >= 0 && weightMatrix[k][j] + gold[i] > j + 1) {
                    k--;
                }
                weightMatrix[i][j] += (k == -1) ? 0 : weightMatrix[k][j];
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return weightMatrix[weightMatrix.length - 1][weightMatrix[0].length - 1];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
