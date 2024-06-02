package by.it.group310901.navrosuk.lesson07;

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
    // Метод для вычисления редакционного расстояния Левенштейна и предписания операций
    String getDistanceEdinting(String one, String two) {
        // Длина первой и второй строки
        int n = one.length();
        int m = two.length();
        // Создаем двумерный массив для хранения расстояний
        int[][] d = new int[n + 1][m + 1];

        // Инициализируем первый столбец: расстояние от пустой строки до i-ого префикса первой строки
        for (int i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // Инициализируем первую строку: расстояние от пустой строки до j-ого префикса второй строки
        for (int j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Заполняем таблицу расстояний
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Определяем стоимость операции замены: 0, если символы совпадают, 1, если не совпадают
                int eq = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;
                // Вычисляем минимальную стоимость между удалением, вставкой и заменой
                d[i][j] = Math.min(d[i - 1][j] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j - 1] + eq));
            }
        }

        // Строим строку с результатом предписания операций
        StringBuilder result = new StringBuilder();

        int i = n;
        int j = m;
        while (i > 0 || j > 0) {
            // Если текущая операция - удаление
            if (i > 0 && d[i - 1][j] + 1 == d[i][j]) {
                result.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            }
            // Если текущая операция - вставка
            else if (j > 0 && d[i][j - 1] + 1 == d[i][j]) {
                result.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            }
            // Если текущая операция - замена или совпадение
            else if (i > 0 && j > 0 && d[i - 1][j - 1] + ((one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1) == d[i][j]) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    result.insert(0, "#,");
                } else {
                    result.insert(0, "~" + two.charAt(j - 1) + ",");
                }
                i--;
                j--;
            }
        }

        // Возвращаем результат в виде строки
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