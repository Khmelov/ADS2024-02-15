package by.it.group351002.zakrevskaya.lesson07;//

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

// Объявление класса A_EditDist
public class A_EditDist {

    private int getDistanceEditingRecursive(String one, String two, int m, int n) {
        // Метод для рекурсивного вычисления расстояния редактирования между строками one и two
        if (m == 0) {
            return n;
        }
        // Если строка one пустая, расстояние равно длине строки two
        if (n == 0) {
            return m;
        }
        // Если строка two пустая, расстояние равно длине строки one
        if (one.charAt(m - 1) == two.charAt(n - 1)) {
            return getDistanceEditingRecursive(one, two, m - 1, n - 1);
        }
        // Если последние символы строк одинаковы, идем к следующим символам
        return 1 + Math.min(
                getDistanceEditingRecursive(one, two, m - 1, n - 1), // Замена
                Math.min(
                        getDistanceEditingRecursive(one, two, m, n - 1), // Вставка
                        getDistanceEditingRecursive(one, two, m - 1, n) // Удаление
                )
        );
        // Возвращаем минимальное расстояние редактирования, учитывая все три возможности: замену, вставку и удаление
    }
    int getDistanceEdinting(String one, String two)
    // Метод для получения расстояния редактирования между строками one и two
    {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return getDistanceEditingRecursive(one, two, one.length(), two.length());
        // Вызов рекурсивного метода getDistanceEditingRecursive с начальными параметрами
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }



    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
