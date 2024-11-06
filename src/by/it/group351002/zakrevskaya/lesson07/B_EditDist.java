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
    Итерационно вычислить расстояние редактирования двух данных непустых строк

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

public class B_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int n = two.length();// Получаем длину второй строки
        int m = one.length();// Получаем длину первой строки
        int[] prevRow = new int[n + 1];// Создаем массив для предыдущей строки длиной n + 1
        int[] currRow = new int[n + 1];// Создаем массив для текущей строки длиной n + 1
        for (int j = 0; j <= n; j++) {// Итерируемся от 0 до n
            prevRow[j] = j;// Инициализируем элементы массива предыдущей строки значениями от 0 до n
        }
        for (int i = 1; i <= m; i++) {// Итерируемся по символам первой строки
            currRow[0] = i;// Инициализируем первый элемент текущей строки значением i
            for (int j = 1; j <= n; j++) {// Итерируемся по символам второй строки
                if (one.charAt(i - 1) == two.charAt(j - 1)) {// Если символы текущих позиций двух строк совпадают
                    currRow[j] = prevRow[j - 1];// Текущий элемент текущей строки равен соответствующему элементу предыдущей строки
                } else {// Если символы не совпадают
                    // Текущий элемент текущей строки равен минимальному из трех значений плюс один
                    currRow[j] = Math.min(Math.min(prevRow[j], currRow[j - 1]), prevRow[j - 1]) + 1;
                }
            }
            // Копируем значения из текущей строки в предыдущую строку для использования на следующей итерации
            System.arraycopy(currRow, 0, prevRow, 0, n + 1);
        }
        // Возвращаем значение последнего элемента текущей строки, которое представляет расстояние Левенштейна между двумя строками
        return currRow[n];

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_EditDist.class.getResourceAsStream("dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }

}
