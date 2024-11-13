package by.it.group310901.pinchuk.lesson07;

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

    int diff(char a, char b) {
        if (a == b) return 0;
        else return 1;
    }


    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!

        int n = one.length() + 1;
        int m = two.length() + 1;
        int[][] d = new int[n][m];

        for (int i = 0; i < n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            d[0][j] = j;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                d[i][j] = Math.min(Math.min( d[i-1][j] + 1, d[i][j-1] + 1), d[i-1][j-1] + diff(one.charAt(i-1), two.charAt(j-1)) );
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        StringBuilder result = new StringBuilder();
        int i = one.length();
        int j = two.length();

        while (i > 0 && j > 0) {
            if (d[i][j] == d[i][j - 1] + 1) {
                result.insert(0, ",");
                result.insert(0, two.charAt(i - 1));
                result.insert(0, "+");
                j--;
            } else if (d[i][j] == d[i - 1][j] + 1) {
                result.insert(0, ",");
                result.insert(0, one.charAt(i - 1));
                result.insert(0, "-");
                i--;
            } else {
                if ((one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1) == 0) {
                    result.insert(0, ",");
                    result.insert(0, "#");
                } else {
                    result.insert(0, ",");
                    result.insert(0, two.charAt(j - 1));
                    result.insert(0, "~");
                }
                i--;
                j--;
            }
        }
        if (i > 0) {
            result.insert(0, ",");
            result.insert(0, one.charAt(i - 1));
            result.insert(0, "-");
        } else if (j > 0) {
            result.insert(0, ",");
            result.insert(0, two.charAt(j - 1));
            result.insert(0, "+");
        }

        return result.toString();
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