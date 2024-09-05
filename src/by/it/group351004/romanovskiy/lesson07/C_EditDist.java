package by.it.group351004.romanovskiy.lesson07;

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

    String getDistanceEdinting(String one, String two) {  // Метод для вычисления редакционного предписания между двумя строками
        int[][] dp = new int[one.length() + 1][two.length() + 1];  // Создаем двумерный массив для хранения значений динамического программирования

        for (int i = 0; i <= one.length(); i++) {  // Итерируемся по символам первой строки
            for (int j = 0; j <= two.length(); j++) {  // Итерируемся по символам второй строки
                if (i == 0) {  // Если индекс i равен 0
                    dp[i][j] = j;  // Значение в массиве равно индексу j (инициализация первой строки)
                } else if (j == 0) {  // Если индекс j равен 0
                    dp[i][j] = i;  // Значение в массиве равно индексу i (инициализация первого столбца)
                } else {  // Иначе
                    // Вычисляем значение для текущей ячейки массива по формуле минимального из трех значений
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1));
                }
            }
        }

        int i = one.length(), j = two.length();  // Инициализация индексов для обратного прохода по массиву
        String result = "";  // Переменная для хранения редакционного предписания

        // Обратный проход по массиву, чтобы восстановить редакционное предписание
        while (i > 0 && j > 0) {
            if (one.charAt(i - 1) == two.charAt(j - 1)) {  // Если символы на соответствующих позициях совпадают
                result+= "#,";  // Добавляем символ копирования в редакционное предписание
                i--;  // Переходим к следующим символам обеих строк
                j--;
            } else if (dp[i][j] == dp[i - 1][j - 1] + 1) {  // Если текущее значение равно значению по диагонали плюс один
                result+="~"+two.charAt(j - 1) + ",";  // Добавляем символ замены в редакционное предписание
                i--;  // Переходим к следующим символам обеих строк
                j--;
            } else if (dp[i][j] == dp[i][j - 1] + 1) {  // Если текущее значение равно значению слева плюс один
                result+= "+" + two.charAt(j - 1)+ ",";  // Добавляем символ вставки в редакционное предписание
                j--;  // Переходим к следующему символу второй строки
            } else {  // Иначе
                result+="-"+one.charAt(i - 1)+",";  // Добавляем символ удаления в редакционное предписание
                i--;  // Переходим к следующему символу первой строки
            }
        }

        // Добавляем в редакционное предписание оставшиеся символы первой строки (если они есть)
        while (i > 0) {
            result+="-"+one.charAt(i - 1)+",";
            i--;
        }

        // Добавляем в редакционное предписание оставшиеся символы второй строки (если они есть)
        while (j > 0) {
            result+="+"+two.charAt(j - 1)+",";
            j--;
        }
        return result;  // Возвращаем редакционное предписание
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