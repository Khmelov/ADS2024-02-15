package by.it.group351001.ivan_shaminko.lesson07;

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
        int[][] def = new int[one.length() + 1][two.length() + 1];
        for (int i = 0; i <= one.length(); i++){
            for (int k = 0; k <= two.length(); k++){
                if (k == 0){
                    def[i][k] = i;
                } else if (i == 0) {
                    def[i][k] = k;
                } else {
                    def[i][k] = Math.min(Math.min(def[i - 1][k], def[i][k - 1]) + 1, def[i - 1][k - 1] + (one.charAt(i - 1) == two.charAt(k - 1) ? 0 : 1));
                    // Если символы равны, то 0, если нет, то +1
                    //Math.min применим только для двух аргументов
                }
            }
        }


        StringBuilder outResult = new StringBuilder();
        int i = one.length();
        int k = two.length();

        while (i > 0 && k > 0){

            if (def[i][k] == def[i][k - 1] + 1) {
                outResult.append("+");
                outResult.append(two.charAt(k - 1));
                outResult.append(",");
                k--;

            } else if (def[i][k] == def[i - 1][k] + 1) {
                outResult.append("-");
                outResult.append(one.charAt(i - 1));
                outResult.append(",");
                i--;

            } else if (def[i][k] == def[i - 1][k - 1] + 1)  {
                outResult.append("~");
                outResult.append(two.charAt(k - 1));
                outResult.append(",");
                i--;
                k--;
            }
            else {
                outResult.append("#,");
                i--;
                k--;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return outResult.toString();
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