package by.it.group351005.brezgunov.lesson07;

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
    String oneString, twoString;

    String createAnswer(int[][] matrix, int i, int j) {
        if (i == 0 && j == 0) {
            return "";
        }
        if (i > 0 && j > 0 && matrix[i][j] == matrix[i - 1][j - 1] && oneString.charAt(i - 1) == twoString.charAt(j - 1)) {
            return createAnswer(matrix, i - 1, j - 1) + "#,";
        } else if (i > 0 && j > 0 && matrix[i][j] == matrix[i - 1][j - 1] + 1) {
            return createAnswer(matrix, i - 1, j - 1) + "~" + twoString.charAt(j - 1) + ",";
        } else if (i > 0 && matrix[i][j] == matrix[i - 1][j] + 1) {
            return createAnswer(matrix, i - 1, j) + "-" + oneString.charAt(i - 1) + ",";
        } else {
            return createAnswer(matrix, i, j - 1) + "+" + twoString.charAt(j - 1) + ",";
        }
    }

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        String answer = "";
        this.oneString = one;
        this.twoString = two;
        int[][] matrix = new int[one.length() + 1][two.length() + 1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                matrix[i][j] = (one.charAt(i - 1) == two.charAt(j - 1)) ? matrix[i - 1][j - 1] : Math.min(Math.min(matrix[i - 1][j - 1], matrix[i - 1][j]), matrix[i][j - 1]) + 1;
            }
        }

        answer = createAnswer(matrix, matrix.length - 1, matrix[0].length - 1);

        return answer;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }

}