package by.it.group351001.polozkov.lesson07;

import java.io.FileInputStream;
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

        int len1 = one.length();
        int len2 = two.length();

        int[][] mass = new int[len1 + 1][len2 + 1];

        // Инициализация первой строки и первого столбца
        for (int i = 0; i <= len1; i++) {
            mass[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            mass[0][j] = j;
        }

        // Заполнение таблицы динамического программирования
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    mass[i][j] = mass[i - 1][j - 1];
                } else {
                    mass[i][j] = 1 + Math.min(mass[i - 1][j], Math.min(mass[i][j - 1], mass[i - 1][j - 1]));
                }
            }
        }



        String result = "";
        int i = len1, j = len2;
        while (i > 0 && j > 0) {
            if (one.charAt(i - 1) == two.charAt(j - 1)) {
                result = "#" + ","+result;
                i--;
                j--;
            } else if (mass[i][j] == mass[i - 1][j - 1] + 1) {
                result = "~" + two.charAt(j - 1) + ","+result;
                i--;
                j--;
            } else if (mass[i][j] == mass[i - 1][j] + 1) {
                result = "-" + one.charAt(i - 1) + ","+result;
                i--;
            } else if (mass[i][j] == mass[i][j - 1] + 1) {
                result = "+" + two.charAt(j - 1) + ","+result;
                j--;
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
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