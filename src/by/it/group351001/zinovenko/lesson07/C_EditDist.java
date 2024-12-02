package by.it.group351001.zinovenko.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    public String getDistanceEdinting(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        char[][] operations = new char[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
            operations[i][0] = '-';
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
            operations[0][j] = '+';
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    operations[i][j] = '#';
                } else {
                    int replace = dp[i - 1][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    int insert = dp[i][j - 1] + 1;
                    int min = Math.min(replace, Math.min(delete, insert));
                    if (min == replace) {
                        dp[i][j] = replace;
                        operations[i][j] = '~';
                    } else if (min == delete) {
                        dp[i][j] = delete;
                        operations[i][j] = '-';
                    } else {
                        dp[i][j] = insert;
                        operations[i][j] = '+';
                    }
                }
            }
        }
        List<String> editInstructions = new ArrayList<>();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            char operation = operations[i][j];
            if (operation == '#') {
                editInstructions.add("#");
                i--;
                j--;
            } else if (operation == '-') {
                editInstructions.add("-" + s1.charAt(i - 1));
                i--;
            } else if (operation == '+') {
                editInstructions.add("+" + s2.charAt(j - 1));
                j--;
            } else if (operation == '~') {
                editInstructions.add("~" + s2.charAt(j - 1));
                i--;
                j--;
            }
        }
        while (i > 0) {
            editInstructions.add("-" + s1.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            editInstructions.add("+" + s2.charAt(j - 1));
            j--;
        }
        // Разворачиваем список операций, чтобы порядок был от начала до конца строк
        StringBuilder sb = new StringBuilder();
        for (int k = editInstructions.size() - 1; k >= 0; k--) {
            sb.append(editInstructions.get(k)).append(",");
        }
        return sb.toString();
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