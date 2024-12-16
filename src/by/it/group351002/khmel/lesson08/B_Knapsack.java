package by.it.group351002.khmel.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.BitSet;
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
/*В этой конкретной задаче каждый предмет (золотой слиток) можно использовать не более одного раза.*/
public class B_Knapsack {
/*Решение динамического программирования реализовано с использованием логического массива dpразмером W+1.
Показывает dp[i], можно ли достичь общего веса.
Логический массив dp инициализируется false значениями, за исключением dp[0]- общего веса 0, не беря никаких золотых слитков).
Решение вычисляется с использованием следующих шагов:

За каждый золотой слиток iвесом gold[i]:

Итерация от максимальной  Wдо gold[i].
Для каждой емкости xот W - gold[i]до 0:
Если возможно достичь общего веса x( dp[x]is true), то также возможно достичь общего веса x + gold[i]путем
 включения текущего золотого слитка i. Обновить dp[x + gold[i]]до true.

После заполнения dpмассива метод находит максимальный вес, которого можно достичь, выполняя итерацию от
Wнуля до 0 и возвращая первый индекс, iдля которого dp[i]равен true.*/
    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int w=scanner.nextInt();
        int n=scanner.nextInt();
        int gold[]=new int[n];
        for (int i = 0; i < n; i++) {
            gold[i]=scanner.nextInt();
        }

        boolean[] dp = new boolean[w+1];
        Arrays.fill(dp,false);
        dp[0] = true;
        for (int i = 0; i < n; i++)
            for (int x = w - gold[i]; x >= 0; x--)
                dp[x + gold[i]] |= dp[x];
        for(int i=w;true;i--)
            if(dp[i])
                return i;
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}