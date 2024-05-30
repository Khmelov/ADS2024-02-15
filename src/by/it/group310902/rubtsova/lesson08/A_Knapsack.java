package by.it.group310902.rubtsova.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

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
        Scanner scanner = new Scanner(stream);
        // вес рюкзака
        int w=scanner.nextInt();
        // количество вариантов слитков
        int n=scanner.nextInt();
        // веса слитков
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }
        //сортируем веса слитков
        Arrays.sort(gold);
        //массив для хранения значений
        int dp[]=new int[w+1];
        //идём по массиву с весами слитков
        for(int i=1;i<=w;++i){
            //идём по всем вариантам слитков
            for(int j=0;j<n;++j){
                //если вес слитка меньше веса рюкзака к данному шагу
                if(gold[j]<=i){
                    //выбираем наибольшее значение
                    dp[i]=Math.max(dp[i],dp[i-gold[j]]+gold[j]);
                }
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // возвращаем максимальный вес
        return dp[w];
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
