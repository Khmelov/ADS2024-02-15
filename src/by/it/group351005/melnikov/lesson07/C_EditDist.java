package by.it.group351005.melnikov.lesson07;

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
    static int M, N;
    static int[][] D;
    int min(int a, int b, int c) {
        if (a <= b && a <= c)
            return a;
        if (b <= a && b <= c)
            return b;
        return c;
    }
    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // Инициализация переменных длины строк
        int M = one.length();
        int N = two.length();

// Создание двумерного массива для хранения промежуточных значений расстояния
        int[][] D = new int[M + 1][N + 1];

// Заполнение базовых случаев
// Расстояние от пустой строки к пустой строке равно 0
        D[0][0] = 0;

// Заполнение первой строки: стоимость вставки символов из строки two
        for (int j = 0; j <= N; j++)
            D[0][j] = j;

// Заполнение первого столбца: стоимость удаления символов из строки one
        for (int i = 0; i <= M; ++i)
            D[i][0] = i;

// Заполнение таблицы расстояний
        for (int i = 1; i <= M; ++i) {
            for (int j = 1; j <= N; ++j) {
                // Выбор минимальной стоимости операции: удаление, вставка или замена/совпадение
                D[i][j] = Math.min(
                        Math.min(D[i - 1][j] + 1,       // Удаление
                                D[i][j - 1] + 1),      // Вставка
                        D[i - 1][j - 1] + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1)  // Замена/совпадение
                );
            }
        }

// Восстановление последовательности операций
        int i = M;
        int j = N;
        StringBuilder result = new StringBuilder();

// Обратный проход по таблице для определения последовательности операций
        while (i > 0 || j > 0) {
            if (i > 0 && D[i - 1][j] + 1 == D[i][j]) {
                // Операция удаления символа из строки one
                result.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            } else if (j > 0 && D[i][j - 1] + 1 == D[i][j]) {
                // Операция вставки символа из строки two
                result.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else if (i > 0 && j > 0 && D[i - 1][j - 1] + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1) == D[i][j]) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    // Операция совпадения символов (без изменений)
                    result.insert(0, "#,");
                } else {
                    // Операция замены символа из строки one на символ из строки two
                    result.insert(0, "~" + two.charAt(j - 1) + ",");
                }
                i--;
                j--;
            }
        }

// Вывод результата
        System.out.println(result.toString());
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
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