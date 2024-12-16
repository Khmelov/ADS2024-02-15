package by.it.group310902.kasperets.lesson07;

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
    public int getDistanceEdinting(String one, String two) {
        return editDistance(one, two, one.length(), two.length());
    }

    private int editDistance(String one, String two, int m, int n) {
        // Базовые случаи
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }

        // Если последние символы совпадают, игнорируем их
        if (one.charAt(m - 1) == two.charAt(n - 1)) {
            return editDistance(one, two, m - 1, n - 1);
        }

        // Иначе, выбираем минимальную операцию (удаление, вставка, замена)
        return 1 + Math.min(
                editDistance(one, two, m, n - 1), // Вставка
                Math.min(
                        editDistance(one, two, m - 1, n), // Удаление
                        editDistance(one, two, m - 1, n - 1) // Замена
                )
        );
    }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
