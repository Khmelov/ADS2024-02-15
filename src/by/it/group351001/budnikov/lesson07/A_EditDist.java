package by.it.group351001.budnikov.lesson07;

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

    String one, two;
    int m, n;
    int[][] d;

    int diff(char a, char b) {
        if (a == b) return 0;
        else return 1;
    }
    int EditDistTd(int i, int j) {

        if (i == 0) return j;
        if (j == 0) return i;
        else {
            int ins = EditDistTd(i, j-1) + 1;
            int del = EditDistTd(i-1, j) + 1;
            int sub = EditDistTd(i-1, j-1) + diff(one.charAt(i-1), two.charAt(j-1));
            d[i-1][j-1] = Math.min(Math.min(ins, del), sub);
        }
        return d[i-1][j-1];
    }
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!


        int result = 0;
        this.one = one;
        this.two = two;
        this.n = one.length();
        this.m = two.length();
        this.d = new int[n][m];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                this.d[i][j] = 2147483647;
            }
        }



        /*
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.println();
        }
         */
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return EditDistTd(n, m);
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
