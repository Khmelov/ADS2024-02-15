package by.it.group351004.zalivako.lesson07;

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

    static int diff(char first, char second){
        if (first == second) return 0;
        return 1;
    }

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int n = one.length();
        int m = two.length();
        //инициализация матрицы
        int[][] d = new int[n+1][m+1];
        //нулевая строка и колонка
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;
        //заполняется вся остальная матрица
        for (int i = 1; i <= n; i++){
            for (int j = 1; j <= m; j++){
                int ins = d[i][j-1]+1;
                int del = d[i-1][j]+1;
                int rep = d[i-1] [j-1]+diff(one.charAt(i-1), two.charAt(j-1));

                //d[i][j] - минимальный среди трех (для оптимального выбора)
                int temp = Math.min(ins, del);
                d[i][j] = Math.min(temp, rep);
            }
        }

        //восстановим решение
        StringBuilder str = new StringBuilder();
        int i = n;
        int j = m;
        while (i != 0 && j != 0) {
            if (d[i][j] == d[i-1][j] + 1) {
                str.insert(0, "-"+one.charAt(i-1)+",");
                i--;
            }
            else if (d[i][j] == d[i][j-1] + 1) {
                str.insert(0, "+"+two.charAt(j-1)+",");
                j--;
            }
            else if (d[i][j] == d[i-1][j-1]){
                str.insert(0, "#,");
                i--;
                j--;
            }
            else {
                str.insert(0, "~"+two.charAt(i-1)+",");
                i--;
                j--;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return str.toString();
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