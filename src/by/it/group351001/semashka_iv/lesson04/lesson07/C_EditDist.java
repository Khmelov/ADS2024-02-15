package by.it.group351001.semashka_iv.lesson04.lesson07;

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

    int m(int i0, int j0, String s1, String s2) {

        i0--;

        j0--;

        if (s1.charAt(i0) == s2.charAt(j0)) {

            return 0;

        } else {

            return 1;

        }

    }

    int min(int n1, int n2, int n3) {

        if (n1 > n2) {

            n1 = n2;

        }

        if (n1 > n3) {

            n1 = n3;

        }

        return n1;

    }


    String getDistanceEdinting(String one, String two) {

        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        /*Метод m: Этот метод сравнивает символы в двух строках.
        Если символы равны, метод возвращает 0, иначе 1.

Метод min: Этот метод принимает три числа и возвращает минимальное из них.

Метод getDistanceEditing:
В этом методе происходит вычисление расстояния Левенштейна и формирование редакционного предписания.

Создается двумерный массив matrix,
где matrix[i][j] хранит расстояние Левенштейна между подстроками
one.substring(0, i) и two.substring(0, j).
Заполняется первая строка и первый столбец матрицы в соответствии с базовыми случаями.
Далее происходит заполнение остальных ячеек матрицы в соответствии с рекуррентным соотношением:
Если символы one.charAt(i - 1) и two.charAt(j - 1) равны,
то значение matrix[i][j] равно значению в верхней левой ячейке.
Иначе выбирается минимальное значение из трех возможных случаев:
Удаление символа из one (matrix[i - 1][j] + 1),
Вставка символа из two (matrix[i][j - 1] + 1),
Замена символа (matrix[i - 1][j - 1] + m(i, j, one, two)).
После заполнения матрицы формируется строка result, которая содержит редакционное предписание:
Если символы равны, добавляется #.
Если символы не равны,
и значение в текущей ячейке равно значению в верхней левой ячейке плюс 1,
то добавляется ~ и символ из строки two.
Если значение в текущей ячейке равно значению в ячейке слева плюс 1,
то добавляется - и символ из строки one.
Если значение в текущей ячейке равно значению в ячейке сверху плюс 1,
то добавляется + и символ из строки two.
Этот процесс продолжается до тех пор, пока i и j больше 0.
Возвращается строка result.
Метод main: В этом методе происходит чтение данных из файла dataABC.txt,
вызов метода getDistanceEditing для каждой пары строк и вывод результата на экран.*/

        int n = one.length();

        int m = two.length();

        int[][] matrix = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {

            for (int j = 0; j <= m; j++) {

                if ((i == 0) && (j == 0)) {

                    matrix[i][j] = 0;

                } else if (j == 0) {

                    matrix[i][j] = i;

                } else if (i == 0) {

                    matrix[i][j] = j;

                } else {

                    matrix[i][j] = min(matrix[i][j - 1] + 1, matrix[i - 1][j] + 1, matrix[i - 1][j - 1] + m(i, j, one, two));

                }


            }

        }


        String result = "";

        int i = n, j = m;

        while (i > 0 && j > 0) {

            if (one.charAt(i - 1) == two.charAt(j - 1)) {

                result = "#" + "," + result;

                i--;

                j--;

            } else if (matrix[i][j] == matrix[i - 1][j - 1] + 1) {

                result = "~" + two.charAt(j - 1) + "," + result;

                i--;

                j--;

            } else if (matrix[i][j] == matrix[i - 1][j] + 1) {

                result = "-" + one.charAt(i - 1) + "," + result;

                i--;

            } else if (matrix[i][j] == matrix[i][j - 1] + 1) {

                result = "+" + two.charAt(j - 1) + "," + result;

                j--;

            }

        }

/*

        while (i > 0) {

            result = "-" + one.charAt(i - 1) + ","+result;

            i--;

        }



        while (j > 0) {

            result = "+" + two.charAt(j - 1) + ","+result;

            j--;

        }*/


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        return result;

    }


    public static void main(String[] args) throws FileNotFoundException {

        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");

        C_EditDist instance = new C_EditDist();

        Scanner scanner = new Scanner(stream);

        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));

        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));

        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));

    }


}