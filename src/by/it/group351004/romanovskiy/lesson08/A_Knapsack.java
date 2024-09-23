package by.it.group351004.romanovskiy.lesson08;

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

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // Создаем объект Scanner для считывания входных данных
        Scanner scanner = new Scanner(stream);
        // Считываем вместимость рюкзака
        int w = scanner.nextInt();
        // Считываем количество вариантов золотых слитков
        int n = scanner.nextInt();
        // Создаем массив для хранения весов слитков
        int gold[] = new int[n];
        // Заполняем массив весами слитков
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Сортируем веса слитков по убыванию
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (gold[i] < gold[j]) {
                    int temp = gold[i];
                    gold[i] = gold[j];
                    gold[j] = temp;
                }
            }
        }

        int result = 0;

        // Перебираем слитки и добавляем их в рюкзак, пока есть свободное место
        for (int i = 0; i < n; i++) {
            if (w >= gold[i]) {
                w -= gold[i];
                result += gold[i];
                i--;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
