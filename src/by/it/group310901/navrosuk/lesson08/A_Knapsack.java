package by.it.group310901.navrosuk.lesson08;

import by.it.a_khmelev.lesson07.A_EditDist;

import java.io.FileInputStream;
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

        // Считываем веса слитков
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Массив для хранения максимального веса для каждой возможной вместимости рюкзака
        int[] d = new int[w + 1];
        // Начальная инициализация массива
        d[0] = 0;

        // Основной цикл для вычисления максимального веса золота
        for (int i = 1; i <= w; i++) {
            d[i] = d[i - 1];
            for (int k = 0; k < n && gold[k] <= i; k++) {
                d[i] = Math.max(d[i], gold[k] + d[i - gold[k]]);
            }
        }

        // Возвращаем максимальный вес, который можно унести
        return d[w];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
