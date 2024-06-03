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

public class A_EditDist {


    int distLeven(int i, int k, String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

                if (k == 0 && i > 0){
                    return i;
                } else if (i == 0 && k > 0) {
                    return k;
                } else if (i == 0 && k == 0) {
                    return 0;
                } else {
                    int buf;
                    if (one.charAt(i - 1) == two.charAt(k - 1)) {
                        buf = 0;
                    } else{
                        buf = 1;
                    }
                    return Math.min(Math.min(distLeven(i, k - 1, one, two) + 1,
                            distLeven(i - 1, k, one, two) + 1),
                            distLeven(i - 1, k - 1, one, two) + buf);
                    // Если символы равны, то 0, если нет, то +1
                    //Math.min применим только для двух аргументов
                }

    }
    int getDistanceEdinting(String one, String two) {

        return distLeven(one.length(), two.length(), one, two);
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
