package by.it.group351004.purenok.lesson07;

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
    private static int calculateDistanceRecursive(String s1, String s2, int m, int n) {
        // Базовые случаи
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }

        // Если символы совпадают
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return calculateDistanceRecursive(s1, s2, m - 1, n - 1);
        }

        // Выбираем минимум из вставки, удаления и замены
        int insert = calculateDistanceRecursive(s1, s2, m, n - 1) + 1;
        int delete = calculateDistanceRecursive(s1, s2, m - 1, n) + 1;
        int replace = calculateDistanceRecursive(s1, s2, m - 1, n - 1) + 1;

        return Math.min(insert, Math.min(delete, replace));
    }


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int result = calculateDistanceRecursive(one, two, one.length(), two.length());
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