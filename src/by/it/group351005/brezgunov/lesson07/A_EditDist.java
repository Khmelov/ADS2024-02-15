package by.it.group351005.brezgunov.lesson07;

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
    String oneString, twoString;

    int countMinDistance(int i, int j) {
        if (i < 1 && j < 1) {
            return 0;
        } else if (i == 0) {
            return j + 1;
        } else if (j == 0) {
            return i + 1;
        } else if (oneString.charAt(i - 1) == twoString.charAt(j - 1)) {
            return countMinDistance(i - 1, j - 1);
        } else {
            int diag = countMinDistance(i - 1, j - 1);
            int up = countMinDistance(i - 1, j);
            int left = countMinDistance(i, j - 1);
            return Math.min(Math.min(up, diag), left) + 1;
        }
    }

    int getDistanceEdinting(String one, String two) {

        oneString = one;
        twoString = two;

        return countMinDistance(one.length() - 1, two.length() - 1);
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
