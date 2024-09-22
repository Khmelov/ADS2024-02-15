package by.it.group351001.ohremchuk_d.lesson07;

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
    // Метод для нахождения минимального из трех чисел
    int min(int n1, int n2, int n3){
        if (n1>n2){
            n1 = n2;
        }
        if (n1>n3){
            n1 = n3;
        }
        return n1;
    }

    // Метод для определения значения m(i, j) (равен 0, если символы равны, иначе 1)
    int m(int i0, int j0, String s1, String s2){
        i0--;
        j0--;
        if (s1.charAt(i0) == s2.charAt(j0)){
            return 0;
        }
        else{
            return 1;
        }
    }

    // Метод для вычисления расстояния редактирования
    int getDistanceEdinting(String one, String two) {
        // Создание матрицы для хранения промежуточных результатов
        int n = one.length();
        int m = two.length();
        int[][] matrix = new int[n+1][m+1];
        // Заполнение матрицы
        for (int i = 0; i<=n; i++){
            for (int j = 0; j<=m; j++){
                if ((i == 0) && (j == 0)){
                    matrix[i][j] = 0;
                }
                else if (j == 0){
                    matrix[i][j] = i;
                }
                else if (i == 0){
                    matrix[i][j] = j;
                }
                else{
                    matrix[i][j] = min(matrix[i][j-1]+1, matrix[i-1][j]+1, matrix[i-1][j-1]+m(i, j, one, two));
                }
            }
        }
        // Получение и возврат результата
        int result = matrix[n][m];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_EditDist.class.getResourceAsStream("dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        // Вывод результатов для каждой пары строк из входных данных
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }

}
