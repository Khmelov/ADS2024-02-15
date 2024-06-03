package by.it.group351002.abrashin.lesson07;

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
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

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

public class A_EditDist {

    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        int m = one.length();
        int n = two.length();

        int[][] d = new int[m + 1][n + 1];

        for(int i = 0; i<=m;i++){
            for(int j = 0; j<=n;j++){
                d[i][j] = Integer.MAX_VALUE;
            }
        }
        for(int j = 0; j<=n;j++) {
            d[0][j] = j;
        }
        for(int j = 0; j<=m;j++){
            d[j][0] = j;
        }

        LevDist(one, two, d, m, n);

        int result = 0;

        result = d[m][n];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    void LevDist(String s1, String s2, int[][] d, int i, int j) {
        if ((i >= 1 && j >= 1) && (d[i][j] == Integer.MAX_VALUE)) {
            LevDist(s1, s2, d, i - 1, j);
            LevDist(s1, s2, d, i, j - 1);
            LevDist(s1, s2, d, i - 1, j - 1);
            d[i][j] = Integer.min(d[i - 1][j] + 1, Integer.min(d[i][j - 1] + 1, d[i - 1][j - 1] + ((s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1)));
        }
    }





    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
