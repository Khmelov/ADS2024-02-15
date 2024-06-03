package by.it.group351002.golovko.lesson07;

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

    private int recursiveHelper(String one, String two, int[][] matrix,int i,int j){
        if (i == 0)
            return j;
        if (j == 0)
            return i;
        int up = recursiveHelper(one, two, matrix, i-1, j) + 1;
        int center = recursiveHelper(one, two, matrix, i-1, j-1) + ((one.charAt(i-1) != two.charAt(j-1)) ? 1 : 0);
        int left = recursiveHelper(one, two, matrix, i, j-1) + 1;
        matrix[i][j] = Math.min((Math.min(center,up)),left);
        return matrix[i][j];
    }
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int oneNewSize = one.length() + 1;
        int twoNewSize = two.length() + 1;
        int[][] temp = new int[oneNewSize][twoNewSize];
        for (int i = 1; i < oneNewSize; i++) {
            for (int j = 1; j < twoNewSize; j++) {
                temp[i][j] = -1;
            }
        }
        for (int i = 1; i < oneNewSize; i++) {
            temp[i][0] = i;
        }
        for (int i = 1; i < twoNewSize; i++) {
            temp[0][i] = i;
        }
        int result = recursiveHelper(one,two,temp,oneNewSize-1,twoNewSize-1);;
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
