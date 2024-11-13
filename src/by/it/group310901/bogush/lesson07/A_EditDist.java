package by.it.group310901.bogush.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

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


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        final int N = one.length() + 1;
        final int M = two.length() + 1;
        int[][] distancesTable = new int[N][M];

        for (int i = 0; i < N; ++i)
            Arrays.fill(distancesTable[i], -1);

        editDistTD(distancesTable, one, two, N - 1, M - 1);

        int result = distancesTable[N - 1][M - 1];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private int editDistTD(int[][] distancesTable, String one, String two, int i, int j){
        if (distancesTable[i][j] == -1)
            if (i == 0)
                distancesTable[i][j] = j;
            else if (j == 0)
                distancesTable[i][j] = i;
            else {
                int ins = editDistTD(distancesTable, one, two, i, j - 1) + 1;
                int del = editDistTD(distancesTable, one, two, i - 1, j) + 1;
                int sub = editDistTD(distancesTable, one, two, i - 1, j - 1) + ((one.charAt(i-1) == two.charAt(j-1)) ? 0 : 1);
                distancesTable[i][j] = Math.min(Math.min(ins, del), sub);
            }
        return distancesTable[i][j];
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
