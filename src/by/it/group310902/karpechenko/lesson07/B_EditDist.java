package by.it.group310902.karpechenko.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/

public class B_EditDist {

    public int dist(String s1, String s2, int i, int j){
        if (i == -1 && j == -1)
            return 0;
        if (i > -1 && j == -1)
            return i + 1;
        if (j > -1 && i == -1)
            return j + 1;
        return Math.min(Math.min(dist(s1,s2, i,j-1) + 1, dist(s1,s2, i -1,j) + 1),dist(s1,s2,i-1,j-1)+ (s1.charAt(i) == s2.charAt(j)? 0:1));
    }
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[][] d = new int[one.length()+1][two.length()+1];
        d[0][0] = 0;
        for (int i = 1; i <= one.length(); i++)
            d[i][0] = i;
        for (int i = 1; i <= two.length(); i++)
            d[0][i] = i;
        for (int i = 1; i <= one.length(); i++)
            for (int j = 1; j <= two.length(); j++)
                d[i][j] = Math.min(Math.min(d[i][j-1]+1,d[i-1][j] + 1),d[i - 1][j - 1]+ (one.charAt(i - 1) == two.charAt(j - 1)? 0:1));
        int result = d[one.length()][two.length()];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_EditDist.class.getResourceAsStream("dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }

}