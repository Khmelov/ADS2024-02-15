package by.it.group351002.khmel.lesson07;

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

/*обеспечивает оптимальную последовательность редактирования для преобразования одной строки в другую.
 Последовательность редактирования представлена в виде строки операций, где каждая операция
 обозначается одним символом: «+» для вставки, «-» для удаления, «~» для замены и «#» для отсутствия изменений
 (копирования).
 /* Расстояние Левенштейна — это минимальное количество односимвольных изменений (вставок, удалений или замен),
необходимых для замены одной строки другой.*/
public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
/*D и P. расстояния редактирования и операции редактирования.*/
        int m = one.length(), n = two.length();
        int[][] D = new int[m + 1][n + 1];
        char[][] P = new char[m + 1][n + 1];
/*

Если символы one[i-1]и two[j-1]одинаковы, минимальной операцией является копирование (без изменений),
обозначаемое знаком «#».
В противном случае рассчитывается минимум три возможности:

Вставка: D[i][j-1] + 1(вставка two[j-1]в one), операция, обозначенная знаком «+».
Удаление: D[i-1][j] + 1(удалить one[i-1]из one), операция обозначается знаком «-».
Замена: D[i-1][j-1] + 1(заменить one[i-1]на two[j-1]), операция обозначается «~».*/
        for (int i = 0; i <= m; i++) {
            D[i][0] = i;
            P[i][0] = '-';
        }
        for (int i = 0; i <= n; i++) {
            D[0][i] = i;
            P[0][i] = '+';
        }
/*После заполнения массивов Dи P метод создает строку последовательности редактирования, ans проходя
P массив назад, начиная с правого нижнего угла.*/
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                int flag = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;
                if(D[i][j - 1] < D[i - 1][j] && D[i][j - 1] < D[i - 1][j - 1] + flag) {
                    D[i][j] = D[i][j - 1] + 1;
                    P[i][j] = '+';
                }
                else if(D[i - 1][j] < D[i - 1][j - 1] + flag) {
                    D[i][j] = D[i - 1][j] + 1;
                    P[i][j] = '-';
                }
                else {
                    D[i][j] = D[i - 1][j - 1] + flag;
                    P[i][j] = (flag == 1) ? '~' : '#';
                }
            }

        StringBuilder ans = new StringBuilder();
        int i = m, j = n;
        while(Math.max(i,j)>0) {
            char c = P[i][j];
            ans.append(c);
            ans.append(',');
            if(c == '~' || c == '#') {
                i--;
                j--;
            }
            else if(c == '-') {
                i--;
            }
            else {
                j--;
            }
        }
        return ans.toString();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group351002/khmel.lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}