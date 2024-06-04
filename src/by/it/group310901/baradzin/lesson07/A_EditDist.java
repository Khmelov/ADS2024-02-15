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
 * <dd>Рекурсивно вычислить расстояние редактирования двух данных непустых строк</dd>
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

public class A_EditDist {
    String one;
    String two;

    public static void main(String[] args) throws FileNotFoundException {
        var stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        assert stream != null;
        var instance = new A_EditDist();
        var scanner = new Scanner(stream);
        for (var i = 0; i < 3; i++)
            System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
    }

    int getDistanceEditing(String one, String two) {
        this.one = one;
        this.two = two;
        return getDistanceEditing(one.length(), two.length());
    }

    int getDistanceEditing(int i, int j) {
        return i == 0 ? j
                : j == 0 ? i
                : Stream.of(
                getDistanceEditing(i, j - 1) + 1,
                getDistanceEditing(i - 1, j) + 1,
                getDistanceEditing(i - 1, j - 1) + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1)
        ).min(Integer::compare).get();
    }
}
