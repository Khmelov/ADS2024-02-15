package by.it.group310901.baradzin.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * <dl>
 * <dt>Задача на программирование: расстояние Левенштейна</dt>
 * <dd><a href="https://ru.wikipedia.org/wiki/Расстояние_Левенштейна">Википедия</a></dd>
 * <dd><a href="http://planetcalc.ru/1721/">planetcalc.ru</a></dd>
 * <dt>Дано:</dt>
 * <dd>Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.</dd>
 * <dt>Необходимо:</dt>
 * <dd>Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ</dd>
 * <dd>Итерационно вычислить алгоритм преобразования двух данных непустых строк</dd>
 * <dd>Вывести через запятую редакционное предписание в формате:</dd>
 * <dd>операция("+" вставка, "-" удаление, "~" замена, "#" копирование)</dd>
 * <dd>символ замены или вставки</dd>
 * </dl>
 * <p>
 * Sample Input 1:<br/>
 * ab<br/>
 * ab<br/>
 * Sample Output 1:<br/>
 * #,#,<br/>
 * <br/>
 * Sample Input 2:<br/>
 * short<br/>
 * ports<br/>
 * Sample Output 2:<br/>
 * -s,~p,#,#,#,+s,<br/>
 * <br/>
 * Sample Input 3:<br/>
 * distance<br/>
 * editing<br/>
 * Sample Output 3:<br/>
 * +e,#,#,-s,#,~i,#,-c,~g,<br/>
 * </p><p>
 * P.S. В литературе обычно действия редакционных предписаний обозначаются так:
 * <dl>
 * <dt>- D (англ. delete)</dt>
 * <dd>удалить</dd>
 * <dt>+ I (англ. insert)</dt>
 * <dd>вставить</dd>
 * <dt>~ R (replace)</dt>
 * <dd>заменить</dd>
 * <dt># M (match)</dt>
 * <dd>совпадение</dd>
 * </dl>
 */

public class C_EditDist {
    int[][] getDistanceEditingMatrix(String one, String two) {
        var matrix = new int[one.length() + 1][two.length() + 1];
        for (var j = 0; j <= two.length(); j++)
            matrix[0][j] = j;
        for (var i = 1; i <= one.length(); i++) {
            matrix[i][0] = i;
            for (var j = 1; j <= two.length(); j++)
                matrix[i][j] = Stream.of(
                        matrix[i][j - 1] + 1,
                        matrix[i - 1][j] + 1,
                        matrix[i - 1][j - 1] + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1)
                ).min(Integer::compare).get();
        }
        return matrix;
    }


    String getDistanceEditing(String one, String two) {
        var matrix = getDistanceEditingMatrix(one, two);

        StringBuilder operations = new StringBuilder();
        for (int i = one.length(), j = two.length(); i > 0 || j > 0; ) {
            if (i > 0 && j > 0 && one.charAt(i - 1) == two.charAt(j - 1)) {
                operations.insert(0, "#,");
                i--;
                j--;
            } else if (i > 0 && (j == 0 || matrix[i][j] == matrix[i - 1][j] + 1)) {
                operations.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            } else if (i == 0 || matrix[i][j] == matrix[i][j - 1] + 1) {
                operations.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else {
                operations.insert(0, "~" + two.charAt(j - 1) + ",");
                i--;
                j--;
            }
        }

        return operations.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        var stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        var instance = new C_EditDist();
        assert stream != null;
        var scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
    }
}