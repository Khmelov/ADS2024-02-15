package by.it.group351003.petrouski.leson7;

import java.io.FileInputStream;
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
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+," вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки
    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,
    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,
    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,
    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

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

    String ReturnDist(int [] [] indexes, int i,int j) {
        String res;
        res = "";
        while ((i != 0)&& (j != 0)) {
            if ((i > 0) && (indexes[i][j] == indexes [i-1][j] + 1)) {
                i--;
                res = res.concat("-,");
            }
            else {
                if ((j > 0)&&(indexes [i][j] == indexes [i][j-1] + 1)) {
                    j--;
                    res = res.concat("+,");
                } else {
                    if ((indexes [i][j] == indexes [i-1][j-1])) {
                        i--;
                        j--;
                        res = res.concat("#,");

                    } else {
                        i--;
                        j--;
                        res = res.concat("~,");
                    }
                }
            }

        }
        res.substring(0,res.length()-2);
        return res;
    }


    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[] [] indexes = new int[one.length()+1] [two.length()+1];
        for (int i = 0; i < one.length()+1; i++) {
            indexes [i] [0] = i;
        }
        for (int i = 0; i < two.length()+1;i++) {
            indexes [0] [i] = i;
        }

        int res = EditDist(one,two, indexes);

        String result = ReturnDist(indexes,one.length(),two.length());
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}