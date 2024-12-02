package by.it.group351002.zakrevskaya.lesson08;

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

    int getMaxSum(InputStream stream ) {
        // Создаем объект Scanner для считывания входных данных
        Scanner scanner = new Scanner(stream);
        // Считываем количество ступенек лестницы
        int n = scanner.nextInt();
        // Создаем массив для хранения значений на ступеньках
        int stairs[] = new int[n];
        // Заполняем массив значениями на ступеньках
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // Переменная для хранения результата
        int result = 0;
        // Массив для хранения максимальной суммы на каждом шаге
        int[] sum = new int[n];
        // Рассматриваем базовые случаи
        if (n == 1) {
            return stairs[0];
        }
        if (n == 2) {
            return stairs[0] + stairs[1];
        }
        // Вычисляем максимальную сумму для первых трех ступенек
        sum[0] = stairs[0];
        sum[1] = stairs[0] + stairs[1];
        sum[2] = Math.max(stairs[0] + stairs[2], stairs[1] + stairs[2]);
        // Вычисляем максимальную сумму для остальных ступенек
        for (int i = 3; i < n; i++) {
            sum[i] = Math.max(sum[i - 2] + stairs[i], sum[i - 3] + stairs[i - 1] + stairs[i]);
        }
        // Результат - максимальная сумма на последней ступеньке
        result = sum[n - 1];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
