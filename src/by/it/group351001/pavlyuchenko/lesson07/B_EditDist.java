package by.it.group351001.pavlyuchenko.lesson07;

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

    int diff(String one, String two,int i, int j) {
        if (one.charAt(i) == two.charAt(j)) {
            return 0;
        } else {
            return 1;
        }
    }

    int EditDist(String one, String two,int[] [] indexes) {
        int ins, del, sub,res,buff;
        res = 0;
        for (int k = 1; k < one.length()+1; k++) {
          for (int n = 1; n < two.length()+1; n++) {
            buff = Integer.min(indexes[k - 1] [n] + 1, indexes [k] [n-1] + 1);
            indexes [k] [n] = Integer.min(buff,indexes[k-1][n-1]+diff(one,two,k-1,n-1));
          }
        }
        return indexes [one.length()] [two.length()] ;
    }

    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[] [] indexes = new int[one.length()+1] [two.length()+1];
        for (int i = 0; i < one.length()+1; i++) {
            indexes [i] [0] = i;
        }
        for (int i = 0; i < two.length()+1;i++) {
            indexes [0] [i] = i;
        }

        int result = EditDist(one,two, indexes);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
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