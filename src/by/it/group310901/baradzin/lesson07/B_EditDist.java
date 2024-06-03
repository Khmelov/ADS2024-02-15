package by.it.group310901.baradzin.lesson07;

import java.io.FileNotFoundException;
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
 * <dd>Итерационно вычислить расстояние редактирования двух данных непустых строк</dd>
 * </dl>
 * <p>
 * Sample Input 1:<br/>
 * ab<br/>
 * ab<br/>
 * Sample Output 1:<br/>
 * 0<br/>
 * <br/>
 * Sample Input 2:<br/>
 * short<br/>
 * ports<br/>
 * Sample Output 2:<br/>
 * 3<br/>
 * <br/>
 * Sample Input 3:<br/>
 * distance<br/>
 * editing<br/>
 * Sample Output 3:<br/>
 * 5<br/>
 * </p>
 */

public class B_EditDist {
    public static void main(String[] args) throws FileNotFoundException {
        var stream = B_EditDist.class.getResourceAsStream("dataABC.txt");
        var instance = new B_EditDist();
        assert stream != null;
        var scanner = new Scanner(stream);
        for (var i = 0; i < 3; i++)
            System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
    }

    int getDistanceEditing(String one, String two) {
        var row = new int[one.length() + 1];
        for (var i = 0; i < row.length; i++)
            row[i] = i;
        for (var i = 1; i <= two.length(); i++) {
            row[0] = i;
            var next = i - 1;
            for (var j = 1; j <= one.length(); j++) {
                var current = Stream.of(
                        row[j] + 1,
                        row[j - 1] + 1,
                        next + (two.charAt(i - 1) == one.charAt(j - 1) ? 0 : 1)
                ).min(Integer::compare).get();
                next = row[j];
                row[j] = current;
            }
        }
        return row[one.length()];
    }
}
