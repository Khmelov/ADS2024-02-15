package by.it.group310902.strizhevskiy.lesson07;

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
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        buf = new int[1+two.length()][1+one.length()];

        for (int j = 1; j <= one.length(); j++) {
            buf[0][j] = j;
        }

        for (int i = 1; i <= two.length(); i++) {
            buf[i][0] = i;
        }

        for (int i = 1; i <= two.length(); i++) {
            for (int j = 1; j <= one.length(); j++) {
                buf[i][j] = -1;
            }
        }

        int result = dist(one, two, one.length(), two.length());
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private int[][] buf;

    private int dist(String one, String two, int j, int i){
        if (buf[i][j] != -1) { return buf[i][j]; }
        
        int u = dist(one, two, j, i-1)+1;
        int d = dist(one, two, j-1, i-1);
        int l = dist(one, two, j-1, i)+1;

        if (one.charAt(j-1) != two.charAt(i-1)) { d++; }

        buf[i][j] = Math.min(Math.min(l,u),d);
        return buf[i][j];
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
