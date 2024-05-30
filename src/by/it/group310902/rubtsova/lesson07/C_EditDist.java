package by.it.group310902.rubtsova.lesson07;

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

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //создаём массив для заполнения
        int[][]dp=new int[one.length()+1][two.length()+1];

        // заполняем первую колонку и первую строку (расстояние от пустой строки до другой строки)
        for (int i = 0; i <= one.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= two.length(); j++) {
            dp[0][j] = j;
        }
        //заполняем оставшийся массив
        for(int i=1;i<=one.length();++i){
            for (int j = 1; j <= two.length(); j++){
                // если символы совпадают, то вес = 0
                int s=(one.charAt(i-1)==two.charAt(j-1))?0:1;
                //считаем минимальный вес
                dp[i][j]=Math.min(Math.min(dp[i-1][j]+1,dp[i][j-1]+1),dp[i-1][j-1]+s);
            }
        }

        // строка для редакционных предписаний
        String result = "";
        //индексы строк
        int i = one.length();
        int j = two.length();

        //идём от правого нижнего угла
        while (i > 0 || j > 0) {
            // если сверху меньше, то удаляем
            if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                result = "-" + one.charAt(i - 1) + "," + result;
                i--;
                // если слева меньше, то вставляем
            } else if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
                result = "+" + two.charAt(j - 1) + "," + result;
                j--;
            } else {
                // если символы не совпадают, то замена
                if (one.charAt(i - 1) != two.charAt(j - 1)) {
                    result = "~" + two.charAt(j - 1) + "," + result;
                    // если символы совпадают, то копируем
                } else {
                    result = "#," + result;
                }
                i--;
                j--;
            }
        }

        return result.trim();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

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