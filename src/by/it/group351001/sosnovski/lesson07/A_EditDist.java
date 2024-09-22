package by.it.group351001.sosnovski.lesson07;

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

        /*Расстояние Левенштейна - это минимальное количество операций вставки,
        удаления и замены, необходимых для превращения одной строки в другую.
        Мы решаем эту задачу с помощью динамического программирования.

        Инициализация: Создается двумерный массив dp, где dp[i][j] будет хранить расстояние Левенштейна
        между подстроками one.substring(0, i) и two.substring(0, j).

        Базовые случаи:
        Первая строка (или столбец) массива dp инициализируется значениями от 0 до m (или n),
        так как расстояние Левенштейна между пустой строкой и любой другой строкой равно длине последней.

        Заполнение таблицы:
        Для каждой пары символов one.charAt(i - 1) и two.charAt(j - 1) проверяется,
        совпадают ли они.
        Если да, то значение dp[i][j] берется из dp[i - 1][j - 1],
        так как никаких операций не требуется. В противном случае, мы выбираем минимальное значение из трех возможных случаев:

        dp[i - 1][j] + 1 (удаление символа из one),
        dp[i][j - 1] + 1 (вставка символа в one),
        dp[i - 1][j - 1] + 1 (замена символа).
        Возврат результата: В конце работы алгоритма значение dp[m][n] содержит искомое расстояние Левенштейна
        между двумя строками.

        Таким образом, после заполнения таблицы, значение dp[m][n] содержит минимальное количество операций,
        необходимых для превращения строки one в строку two. Это и будет результатом работы программы.*/

        int m = one.length();
        int n = two.length();

        int[][] dp = new int[m + 1][n + 1];

        // Инициализация первой строки и первого столбца
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Заполнение таблицы динамического программирования
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }

        int result = dp[m][n];

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
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
