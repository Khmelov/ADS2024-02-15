package by.it.group310902.kashlej.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static java.lang.Math.min;

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

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        String result = "";
        int n=one.length()+1;
        int m=two.length()+1;
        int[][] d=new int[n][m];
        for(int i=0;i<n;i++){
            d[i][0]=i;
        };
        for (int j=0; j<m;j++){
            d[0][j]=j;
        }
        for (int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                int c=(one.charAt(i-1) == two.charAt(j-1) ? 0 : 1);
                d[i][j]=min((d[i-1][j]+1),(d[i][j-1]+1));
                d[i][j]=min(d[i][j],(d[i-1][j-1]+c));

            }
        }
        int i = n-1;
        int j = m-1;
        StringBuilder str=new StringBuilder();
        while (i > 0 || j > 0) {

            if (i > 0 && d[i - 1][j] + 1 == d[i][j]) {
                str.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            } else if (j > 0 && d[i][j - 1] + 1 == d[i][j]) {
                str.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else if (i > 0 && j > 0 && d[i - 1][j - 1] + ((one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1) == d[i][j]) {

                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    str.insert(0, "#,");
                } else {
                    str.insert(0, "~" + two.charAt(j - 1) + ",");
                }

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