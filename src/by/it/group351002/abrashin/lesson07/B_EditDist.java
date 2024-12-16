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

        for(int i = 1; i<=m;i++){
            for(int j = 1; j<=n;j++){
               if (one.charAt(i-1)==two.charAt(j-1)){
                   d[i][j] = d[i-1][j-1];
               } else {
                   d[i][j] = Integer.min(d[i - 1][j] + 1, Integer.min(d[i][j - 1] + 1, d[i - 1][j - 1] + 1));
               }

            }
        }


        int result = 0;

        result = d[m][n];
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