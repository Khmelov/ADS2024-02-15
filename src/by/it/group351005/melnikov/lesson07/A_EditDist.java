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
    static int[][] D;
    static int M, N;
    static String S1;
    static String S2;
    int min(int a, int b, int c) {
        if (a <= b && a <= c)
            return a;
        if (b <= a && b <= c)
            return b;
        return c;
    }
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // Инициализация переменных длины строк
        int M = one.length();
        int N = two.length();

        // Создание двумерного массива для хранения промежуточных значений расстояния
        int[][] D = new int[M + 1][N + 1];

        // Начальная точка (обе строки пустые) имеет расстояние 0
        D[0][0] = 0;

        // Заполнение первой строки: преобразование пустой строки в строку `two`
        for (int j = 0; j <= N; j++)
            D[0][j] = j;

        // Заполнение первого столбца: преобразование строки `one` в пустую строку
        for (int i = 0; i <= M; ++i)
            D[i][0] = i;

        // Заполнение остальной части таблицы
        for (int i = 1; i <= M; ++i) {
            for (int j = 1; j <= N; ++j) {
                // Определение стоимости замены символа
                int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;

                // Выбор минимальной стоимости операции из трех возможных: вставка, удаление, замена
                D[i][j] = Math.min(
                        Math.min(D[i - 1][j] + 1,       // Удаление
                                D[i][j - 1] + 1),      // Вставка
                        D[i - 1][j - 1] + cost);        // Замена
            }
        }

        // Результат - значение в правом нижнем углу таблицы
        int result = D[M][N];

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