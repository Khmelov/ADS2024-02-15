package by.it.group351005.melnikov.lesson08;

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

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);

        // Чтение количества ступеней
        int n = scanner.nextInt();

        // Создание массива для хранения значений ступеней
        int[] stairs = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // Инициализация массива для хранения максимальной суммы до каждой ступени
        int[] D = new int[n + 1];

        // Обработка базовых случаев
        if (n == 0) return 0;
        if (n == 1) return stairs[0];

        // Инициализация значений для первых двух ступеней
        D[0] = 0;            // Начальная точка (не на ступени)
        D[1] = stairs[0];    // Первая ступень

        // Заполнение массива D для всех ступеней, начиная с третьей
        for (int i = 2; i <= n; i++) {
            D[i] = Math.max(D[i - 2], D[i - 1]) + stairs[i - 1];
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        // Возвращение максимальной суммы, которую можно получить, достигнув последней ступени
        return D[n];
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}