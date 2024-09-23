package by.it.group351004.shkredova.lesson07;

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
    static int[][] d;
    static String a, b;
    static int diff(char first, char second){
        if (first == second) return 0;
        return 1;
    }
    static int EditDistTD(int i, int j) {
        if (d[i][j] == Integer.MAX_VALUE){
            if (i == 0) {
                d[i][j] = j;
            }
            else if (j == 0) {
                d[i][j] = i;
            }
            else {
                int del = EditDistTD(i-1, j)+1;
                int ins = EditDistTD(i, j-1)+1;
                int rep = EditDistTD(i-1, j-1) + diff(a.charAt(i-1), b.charAt(j-1));

                //d[i][j] - минимальный среди трех
                int temp = Math.min(ins, del);
                d[i][j] = Math.min(temp, rep);
            }
        }
        return d[i][j];
    }

    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int n = one.length();
        int m = two.length();
        d = new int[n+1][m+1];
        for (int i = 0; i <= n; i++){
            for (int j = 0; j <= m; j++){
                d[i][j] = Integer.MAX_VALUE;
            }
        }
        //а - первая строка, b - вторая строка
        a = one;
        b = two;

        return EditDistTD(n,m);
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
