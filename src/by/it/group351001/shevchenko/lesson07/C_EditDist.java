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
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        String result = EditDistBU(one, two);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    String EditDistBU(String first, String second) {
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

        StringBuilder actionsBuilder = new StringBuilder();
        int i = n;
        int j = m;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && first.charAt(i - 1) == second.charAt(j - 1)) {
                actionsBuilder.insert(0, "#,");
                i--;
                j--;
            } else if (i > 0 && matrix[i][j] == matrix[i - 1][j] + 1) {
                actionsBuilder.insert(0, "-").insert(0, first.charAt(i - 1)).insert(0, ",");
                i--;
            } else if (j > 0 && matrix[i][j] == matrix[i][j - 1] + 1) {
                actionsBuilder.insert(0, "+").insert(0, second.charAt(j - 1)).insert(0, ",");
                j--;
            } else if (i > 0 && j > 0 && matrix[i][j] == matrix[i - 1][j - 1] + 1) {
                actionsBuilder.insert(0, "~").insert(0, second.charAt(j - 1)).insert(0, ",");
                i--;
                j--;
            }
        }

        return actionsBuilder.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}