package by.it.group310902.strizhevskiy.lesson07;

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
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
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

    public String getDistanceEdinting(String one, String two) {
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
                int u = buf[i-1][j]+1;
                int l = buf[i][j-1]+1;
                int d = buf[i-1][j-1];
                if (two.charAt(i-1) != one.charAt(j-1)) { d++; }
                buf[i][j] = Math.min(Math.min(u,l),d);
            }
        }

        String result = dist(one, two, one.length(), two.length(), buf[two.length()][one.length()]);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private int[][] buf;

    private String dist(String one, String two, int j, int i, int lvl){
        if (lvl < 0 || lvl < buf[i][j]) { return null; }
        if (i == 0 && j == 0) { return ""; }
        
        String u = dist(one, two, j, i-1, lvl-1);
        if (u != null) { return u+"+"+two.charAt(i-1)+","; }

        String l = dist(one, two, j-1, i, lvl-1);
        if (l != null) { return l+"-"+one.charAt(j-1)+","; }

        if (i <= 0 || j <= 0) { return null; }
        int cost = one.charAt(j-1) != two.charAt(i-1) ? 1 : 0;

        String d = dist(one, two, j-1, i-1, lvl-cost);
        if (d == null) { return null; }
        
        if (cost == 0) { return d+"#,"; }
        return d+"~"+one.charAt(j-1)+",";
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


