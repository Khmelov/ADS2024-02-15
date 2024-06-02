package by.it.group351001.shevchenko.lesson08;

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
        int w = scanner.nextInt();
        int n = scanner.nextInt();
        int gold[] = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }
        int result = KnapsackWithoutRepsBU(w, gold);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    int KnapsackWithoutRepsBU(int w, int[] goldw) {
        int n = goldw.length;
        int[][] D = new int[w + 1][n + 1];
        for (int i = 0; i <= w; i++) {
            D[i][0] = 0;
        }

        for (int i = 0; i <= n; i++) {
            D[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int curW = 1; curW <= w; curW++) {
                D[curW][i] = D[curW][i - 1];
                if (goldw[i - 1] <= curW) {
                    D[curW][i] = Math.max(D[curW][i], D[curW - goldw[i - 1]][i - 1] + goldw[i - 1]);
                }
            }
        }

        return D[w][n];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
