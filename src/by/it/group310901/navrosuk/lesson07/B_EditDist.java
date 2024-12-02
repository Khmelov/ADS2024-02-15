package by.it.group310901.navrosuk.lesson07;

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
    Итерационно вычислить расстояние редактирования двух данных непустых строк

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

public class B_EditDist {


    // Метод для вычисления расстояния Левенштейна
    int getDistanceEdinting(String one, String two) {
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
                int eq = one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1;
                // Вычисляем минимальную стоимость между удалением, вставкой и заменой
                d[i][j] = Math.min(
                        d[i - 1][j] + 1, // Удаление
                        Math.min(
                                d[i][j - 1] + 1, // Вставка
                                d[i - 1][j - 1] + eq // Замена
                        )
                );
            }
        }

        // Возвращаем расстояние Левенштейна для данных строк
        return d[n][m];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_EditDist.class.getResourceAsStream("dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }

}