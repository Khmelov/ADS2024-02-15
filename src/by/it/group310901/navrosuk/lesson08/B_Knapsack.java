package by.it.group310901.navrosuk.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

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

    // Метод для вычисления максимального веса золота, который можно унести в рюкзаке
    int getMaxWeight(InputStream stream) {
        // Читаем входные данные
        Scanner scanner = new Scanner(stream);
        // Вместимость рюкзака
        int w = scanner.nextInt();
        // Количество вариантов золотых слитков
        int n = scanner.nextInt();
        // Массив для хранения весов слитков
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Массив для хранения информации о возможных весах
        boolean[] d = new boolean[w + 1];
        // Инициализация массива: начальное значение
        Arrays.fill(d, false);
        d[0] = true;  // Вес 0 всегда возможен

        // Основной цикл для вычисления возможных весов рюкзака
        for (int i = 0; i < n; i++) {
            for (int k = w - gold[i]; k >= 0; k--) {
                d[k + gold[i]] |= d[k];
            }
        }

        // Поиск максимального возможного веса
        for (int i = w; true; i--) {
            if (d[i]) {
                return i;
            }
        }



    }



    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
