package by.it.group310901.lisovitskii.lesson07;

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
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/
//Расстояние Левенштейна -
// это минимальное количество односимвольных операций
// (вставка, удаление, замена), необходимых для превращения одной
// строки в другую.
public class A_EditDist {

//происходит вычисление редакционного расстояния между строками one и two.
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int[][] d = new int[one.length() + 1][two.length() + 1];

//каждая ячейка [i][j] содержит текущее редакционное расстояние между подстроками
        for (int i = 0; i <= one.length(); i++) {
            d[i][0] = i;
        }

        for (int i = 0; i <= two.length(); i++) {
            d[0][i] = i;
        }


        for (int i = 1; i <= one.length(); i++) {
//Затем двумерный массив заполняется
            for (int j = 1; j <= two.length(); j++) {

                int insertion = d[i][j - 1] + 1;
                int deletion = d[i - 1][j] + 1;
                int match = d[i - 1][j - 1];
                int mismatch = d[i - 1][j - 1] + 1;
//Далее, для каждой ячейки, рассчитываются стоимости вставки,
// удаления, совпадения и несовпадения. Затем выбирается
// минимальное значение для перехода в текущую ячейку.
                if (one.charAt(i - 1) == two.charAt(j - 1))
                    d[i][j] = Math.min(Math.min(insertion, deletion), match);
                else
                    d[i][j] = Math.min(Math.min(insertion, deletion), mismatch);

            }
        }

        return d[one.length()][two.length()];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }

//Метод main пытается открыть файл "dataABC.txt"
// в качестве входных данных, создает экземпляр класса
// A_EditDist, считывает по две строки из файла и выводит
// на экран редакционные расстояния между ними,
// используя метод getDistanceEdinting.
    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
