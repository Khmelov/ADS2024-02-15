package by.it.group351004.lazuta.lesson07;

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
        return levenshteinDistance(one, one.length(), two, two.length());
    }

    private static int levenshteinDistance(String s1, int m, String s2, int n) {
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }

        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return levenshteinDistance(s1, m - 1, s2, n - 1);
        } else {
            return 1 + Math.min(
                    levenshteinDistance(s1, m - 1, s2, n),
                    Math.min(
                            levenshteinDistance(s1, m, s2, n - 1),
                            levenshteinDistance(s1, m - 1, s2, n - 1)
                    )
            );
        }
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
