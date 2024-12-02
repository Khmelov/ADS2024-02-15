package by.it.group351001.semashka_iv.lesson04.lesson08;

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

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

 /*В методе getMaxWeight происходит чтение входных данных из файла и их подготовка для обработки.
Далее слитки золота сортируются по весу по убыванию,
чтобы при жадном выборе слитков мы сначала брали те, которые тяжелее.
Затем идет жадный процесс выбора слитков:
начинаем с самого тяжелого слитка и пытаемся его положить в рюкзак.
Если его вес не превышает вместимость рюкзака,
то кладем его туда и уменьшаем вместимость рюкзака на вес этого слитка.
Повторяем этот процесс, пока рюкзак не будет заполнен или не останется слитков,
которые можно было бы положить в рюкзак.
Возвращается общий вес слитков, положенных в рюкзак.*/

        Scanner scanner = new Scanner(stream);
        int w=scanner.nextInt();
        int n=scanner.nextInt();
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }
        int result = 0;

        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                if(gold[i] < gold[j]) {
                    int temp = gold[i];
                    gold[i] = gold[j];
                    gold[j] = temp;
                }
            }
        }
        for(int i = 0; i < n; i++) {
            if(w >= gold[i]) {
                w -= gold[i];
                result += gold[i];
            }
        }



        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}