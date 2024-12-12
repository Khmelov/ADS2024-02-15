package by.it.group351001.shevchenko.lesson07;

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

        int result = EditDistBU(one, two);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    int EditDistBU(String first, String second) {
        int n = first.length();
        int m = second.length();

        int[][] matrix = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            matrix[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            matrix[0][j] = j;
        }

        int ins, sub, del, subcost;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                subcost = 0;
                if (first.charAt(i - 1) != second.charAt(j - 1)) {
                    subcost = 1;
                }

                ins = matrix[i][j - 1] + 1;
                del = matrix[i - 1][j] + 1;
                sub = matrix[i - 1][j - 1] + subcost;

                matrix[i][j] = Math.min(ins, Math.min(del, sub));
            }
        }

        return matrix[n][m];
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