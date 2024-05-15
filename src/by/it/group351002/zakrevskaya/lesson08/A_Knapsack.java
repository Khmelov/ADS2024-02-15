package by.it.group351002.zakrevskaya.lesson08;

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

    int getMaxWeight(InputStream stream ) {  // Метод для нахождения максимального веса золота
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);  // Создание объекта Scanner для чтения входных данных
        int w=scanner.nextInt();  // Считывание вместимости рюкзака
        int n=scanner.nextInt();  // Считывание количества вариантов золотых слитков
        int gold[]=new int[n];  // Создание массива для хранения весов слитков
        for (int i = 0; i < n; i++) {  // Цикл для считывания весов слитков
            gold[i]=scanner.nextInt();
        }

        // Сортировка весов слитков по убыванию
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (gold[i] < gold[j]) {
                    int temp = gold[i];
                    gold[i] = gold[j];
                    gold[j] = temp;
                }
            }
        }
        // Переменная для хранения результата
        int result = 0;
        // Перебор слитков и добавление их в рюкзак, пока есть свободное место
        for(int i = 0; i < n; i++) {
            if(w >= gold[i]) {
                w -= gold[i];
                result += gold[i];
                i--;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;// Возвращаем максимальный вес золота
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}