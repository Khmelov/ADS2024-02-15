package by.it.group310901.navrosuk.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Даны число 1<=n<=100 ступенек лестницы и
целые числа −10000<=a[1],…,a[n]<=10000, которыми помечены ступеньки.
Найдите максимальную сумму, которую можно получить, идя по лестнице
снизу вверх (от нулевой до n-й ступеньки), каждый раз поднимаясь на
одну или на две ступеньки.

Sample Input 1:
2
1 2
Sample Output 1:
3

Sample Input 2:
2
2 -1
Sample Output 2:
1

Sample Input 3:
3
-1 2 1
Sample Output 3:
3

*/

public class C_Stairs {

    // Метод для вычисления максимальной суммы, которую можно получить, идя по лестнице
    int getMaxSum(InputStream stream) {
        // Читаем входные данные
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] stairs = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Инициализация переменной для результата
        int result = 0;
        // Массив для хранения максимальной суммы до каждой ступеньки
        int[] d = new int[n];

        // Начальная инициализация: на первой ступеньке максимальная сумма равна значению этой ступеньки
        d[0] = stairs[0];
        // На второй ступеньке максимальная сумма равна значению этой ступеньки плюс максимальная сумма до предыдущей ступеньки
        d[1] = stairs[1] + Math.max(0, d[0]);

        // Заполнение массива d для всех ступенек, начиная с третьей
        for (int i = 2; i < n; i++) {
            d[i] = stairs[i] + Math.max(d[i - 1], d[i - 2]);
        }

        // Возвращаем максимальную сумму для последней ступеньки
        return d[n - 1];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
