package by.it.group351005.melnikov.lesson08;

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

    // Метод для нахождения максимального веса золота, который можно уместить в рюкзак
    int getMaxWeight(InputStream stream) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // Использование Scanner для чтения входных данных из потока
        Scanner scanner = new Scanner(stream);

        // Чтение максимальной вместимости рюкзака
        int w = scanner.nextInt();

        // Чтение количества слитков золота
        int n = scanner.nextInt();

        // Создание массива для хранения весов слитков золота
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Создание двумерного массива для хранения промежуточных значений
        int[][] D = new int[n + 1][w + 1];

        // Заполнение таблицы методом динамического программирования
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                if (gold[i - 1] <= j) {
                    // Если текущий слиток золота может быть добавлен в рюкзак
                    D[i][j] = Math.max(
                            D[i - 1][j],                // Не добавлять текущий слиток
                            D[i - 1][j - gold[i - 1]] + gold[i - 1]  // Добавить текущий слиток
                    );
                } else {
                    // Если текущий слиток золота не может быть добавлен в рюкзак
                    D[i][j] = D[i - 1][j];
                }
            }
        }

        // Возвращение максимального веса, который можно поместить в рюкзак
        return D[n][w];

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}