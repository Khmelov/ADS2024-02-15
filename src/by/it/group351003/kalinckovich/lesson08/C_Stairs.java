package by.it.group351003.kalinckovich.lesson08;

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
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] stairs = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Создаем массив для хранения максимальной суммы до каждой ступеньки
        int[] dp = new int[n];

        // Вычисляем максимальную сумму для первых двух ступенек
        dp[0] = stairs[0];
        dp[1] = Math.max(stairs[0] + stairs[1], stairs[1]);

        // Заполняем массив dp
        for (int i = 2; i < n; i++) {
            // Максимальная сумма, достигнутая на текущей ступеньке,
            // это максимальная сумма, которую мы можем получить, поднявшись на одну ступеньку или две ступеньки из предыдущих ступенек
            dp[i] = Math.max(dp[i - 2] + stairs[i], dp[i - 1] + stairs[i]);
        }

        return dp[n - 1];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
