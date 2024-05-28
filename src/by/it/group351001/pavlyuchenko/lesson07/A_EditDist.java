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

   int diff(String one, String two,int i, int j) {
     if (one.charAt(i) == two.charAt(j)) {
        return 0;
       } else {
         return 1;
     }
   }
    int EditDist(String one, String two,int[] [] indexes,int i, int j) {
        int ins, del, sub,res;
          if (i == 0) {
            res = j;
            } else {
              if (j == 0) {
                  res = i;
              } else {
                  ins = EditDist(one, two,indexes,i -1,j) + 1;
                  del = EditDist(one, two,indexes,i ,j-1) + 1;
                  sub = EditDist(one, two,indexes,i-1,j-1) + diff(one,two,i-1,j-1);
                  res = Integer.min(ins,del);
                  res = Integer.min(res,sub);
              }
          }
          return res;
    }

    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[] [] indexes = new int[one.length()+1] [two.length()+1];
        for (int i = 0; i < one.length()+1; i++) {
            indexes [i] [0] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < two.length()+1;i++) {
            indexes [0] [i] = Integer.MAX_VALUE;
        }


        int result = EditDist(one,two,indexes,one.length(),two.length());
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
